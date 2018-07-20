package cn.captainshen.controller;

import cn.captainshen.entity.Group;
import cn.captainshen.entity.User;
import cn.captainshen.service.GroupService;
import cn.captainshen.entity.LocalFile;
import cn.captainshen.service.FileService;
import cn.captainshen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    @Resource
    private GroupService groupService;
    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/","/index"})
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/register",method = {RequestMethod.GET})
    public String register(){
        return "register";
    }

    @RequestMapping(value = "/exit",method = {RequestMethod.GET})
    public String exit(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:/index";
    }

    @RequestMapping(value = "/search",method = {RequestMethod.GET})
    public String search(HttpSession httpSession,Model model){
        User loginUser = (User) httpSession.getAttribute("loginUser");
        List<Group> createGroupList = groupService.findAllCreateGroup(loginUser.getUserid().toString());
        List<Group> joinGroupList = groupService.findAllJoinGroup(loginUser.getUserid().toString());
        httpSession.setAttribute("createGroupList",createGroupList);
        httpSession.setAttribute("joinGroupList",joinGroupList);
        User user = (User)httpSession.getAttribute("loginUser");
        user = userService.findUserByName(user.getUsername());
        List<LocalFile> ownUploadedFileList = fileService.findFileByUserId(user.getUserid());
        List<LocalFile> ownDownloadedFileList = fileService.findDownloadedFileByUserId(user.getUserid());
        model.addAttribute("ownUploadedFileList", ownUploadedFileList);
        model.addAttribute("ownDownloadedFileList", ownDownloadedFileList);
        model.addAttribute("user", user);
        return "search";
    }

    @RequestMapping(value = "/admin",method = {RequestMethod.GET})
    public String admin(){
        return "admin";
    }

}
