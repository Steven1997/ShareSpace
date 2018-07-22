package cn.captainshen.controller;


import cn.captainshen.dao.FileDao;
import cn.captainshen.entity.LocalFile;
import cn.captainshen.entity.User;
import cn.captainshen.enums.FileUploadStatusEnum;
import cn.captainshen.service.FileService;
import cn.captainshen.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Controller
public class FileController {
    @Autowired
    private FileService fileService;

    @Autowired
    private FileUtil fileUtil;

    /**
     * 文件上传页面
     * @return
     */
    @GetMapping(value = {"/upload"})
    public String testUpload(){
        return "upload";
    }

    /**
     * 文件上传处理
     */
    @PostMapping(value = {"/doUpload"})
    public String upload(@RequestParam("description") String desc,
                         @RequestParam("file")MultipartFile file,
                         @RequestParam("state") String state,
                         HttpSession session,
                         Model model){
        User user = (User) session.getAttribute("loginUser");
        FileUploadStatusEnum uploadResult = fileService.uploadFile(
                user, file, System.currentTimeMillis(), desc, state
        );
        return "redirect:/search?op=1";
    }

    /**
     * 文件下载处理
     * @param fileId
     * @param session
     * @return
     */
    @GetMapping(value = {"/download"})
    public ResponseEntity<byte[]> download( @RequestParam("fileId") int fileId,
                                            HttpSession session){
        User user = (User)session.getAttribute("loginUser");
        LocalFile localFile = fileService.findFileByFileId(fileId);
        File downloadFile = fileService.findRealFileByFileId(fileId);

        if(user == null || downloadFile == null || !fileService.checkDownloadable(user, localFile)){
            return fileUtil.buildErrorResponseEntity();
        }
        ResponseEntity<byte[]> res = fileUtil.buildResponseEntity(downloadFile, fileId);
        if(res != null){
            // 下载信息入库
            fileService.addDownloadRecord(user.getUserid(), fileId);
            System.out.println(user.getUserid());
            //TODO 积分处理
            return res;
        }
        return fileUtil.buildErrorResponseEntity();
    }

    @PostMapping(value = {"/searchPublicFile"})
    public String searchPublicFile(@RequestParam("keywords") String keywords, Model model){
        List<LocalFile> searchResult = fileService.findPublicFilesByFileName(keywords);
        model.addAttribute("searchResult", searchResult);
//        System.out.println(searchResult);
        return "search";
    }

    /**
     * 文件信息修改页面
     * @param fileId
     * @param session
     * @param redirectAttributes
     * @param model
     * @return
     */
    @GetMapping(value = {"/update"})
    public String update(@RequestParam("fileId")int fileId,
                         HttpSession session,
                         RedirectAttributes redirectAttributes,
                         Model model){
        User user = (User)session.getAttribute("loginUser");
        LocalFile file = fileService.findFileByFileId(fileId);
        if(user == null || file == null || file.getUserid() != user.getUserid()){
            redirectAttributes.addFlashAttribute("error_msg", "权限不足");
            return "redirect:/search?op=1";
        }
        model.addAttribute("updatingFile", file);
        return "update";
    }

    /**
     * 文件信息修改处理
     * @param fileDesc
     * @param fileState
     * @param fileId
     * @param redirectAttributes
     * @param session
     * @return
     */
    @PostMapping(value = {"/doUpdate"})
    public String doUpdate(@RequestParam("description") String fileDesc,
                           @RequestParam("state") String fileState,
                           @RequestParam("fileId") int fileId,
                           RedirectAttributes redirectAttributes,
                           HttpSession session){
        User user = (User)session.getAttribute("loginUser");
        LocalFile file = fileService.findFileByFileId(fileId);
        if(user == null || file == null || file.getUserid() != user.getUserid()){
            redirectAttributes.addFlashAttribute("error_msg", "权限不足");
            return "redirect:/search?op=1";
        }
        fileService.updateFileInfo(fileId, fileDesc, fileState);
        redirectAttributes.addFlashAttribute("error_msg", "修改成功");
        return "redirect:/search?op=1";
    }

    @GetMapping(value = "/delete")
    public String doDelete(@RequestParam("fileId") int fileId,
                           HttpSession session,
                           RedirectAttributes redirectAttributes){
        User user = (User)session.getAttribute("loginUser");
        LocalFile file = fileService.findFileByFileId(fileId);
        if(user == null || file == null || file.getUserid() != user.getUserid()){
            redirectAttributes.addFlashAttribute("error_msg", "权限不足");
            return "redirect:/search?op=1";
        }
        fileService.deleteFileByFileId(fileId);
        redirectAttributes.addFlashAttribute("error_msg", "删除成功");
        return "redirect:/search?op=1";
    }
}