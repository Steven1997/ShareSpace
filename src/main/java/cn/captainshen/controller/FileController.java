package cn.captainshen.controller;


import cn.captainshen.entity.User;
import cn.captainshen.enums.FileUploadStatusEnum;
import cn.captainshen.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;

@Controller
public class FileController {
    @Autowired
    private FileUtil fileUtil;
    /**
     * 文件上传测试页面
     * @return
     */
    @GetMapping(value = {"/testUpload"})
    public String testUpload(){
        return "testUpload";
    }

    /**
     * 文件上传处理
     */
    @PostMapping(value = {"/upload"})
    public String upload(@RequestParam("description") String desc,
                         @RequestParam("file")MultipartFile file,
                         HttpSession session,
                         Model model){
        User user = (User) session.getAttribute("loginUser");

        String saveInfo = fileUtil.saveUploadFile(user.getUsername(), file, System.currentTimeMillis()).getStatusInfo();
        System.out.println(saveInfo);
        // 文件保存失败
        if(!saveInfo.equals(FileUploadStatusEnum.SUCCESS.getStatusInfo())){
            model.addAttribute("error_msg", saveInfo);
            return "index";
        }
        return "index";
    }
}