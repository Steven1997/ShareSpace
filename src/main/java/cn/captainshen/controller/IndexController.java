package cn.captainshen.controller;

import cn.captainshen.entity.Group;
import cn.captainshen.entity.User;
import cn.captainshen.service.GroupService;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {
    @Resource
    GroupService groupService;

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
    public String search(HttpSession httpSession){
        User loginUser = (User) httpSession.getAttribute("loginUser");
        List<Group> createGroupList = groupService.findAllCreateGroup(loginUser.getUserid().toString());
        List<Group> joinGroupList = groupService.findAllJoinGroup(loginUser.getUserid().toString());
        httpSession.setAttribute("createGroupList",createGroupList);
        httpSession.setAttribute("joinGroupList",joinGroupList);
        System.out.println(createGroupList);
        System.out.println(joinGroupList);
        return "search";
    }


    @RequestMapping(value = "/admin",method = {RequestMethod.GET})
    public String admin(){
        return "admin";
    }

}
