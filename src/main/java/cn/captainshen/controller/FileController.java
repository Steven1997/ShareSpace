package cn.captainshen.controller;


import cn.captainshen.dao.FileDao;
import cn.captainshen.entity.LocalFile;
import cn.captainshen.entity.User;
import cn.captainshen.enums.FileUploadStatusEnum;
import cn.captainshen.service.FileService;
import cn.captainshen.util.FileUtil;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping(value = {"/download/{fileId}"})
    public ResponseEntity<byte[]> download( @PathVariable("fileId") int fileId,
                                            HttpSession session){
        //TODO 文件下载权限校验(用户组文件,公开文件,私密文件)
        //TODO 下载错误信息返回
        User user = (User)session.getAttribute("loginUser");
        File downloadFile = fileService.findRealFileByFileId(fileId);

        if(user == null || downloadFile == null){
            return fileUtil.buildErrorResponseEntity();
        }


        if(downloadFile == null){
            System.out.println("file not on this disk");
        }
        ResponseEntity<byte[]> res = fileUtil.buildResponseEntity(downloadFile, fileId);
        return res;
    }
}