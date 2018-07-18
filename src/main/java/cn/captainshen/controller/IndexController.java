package cn.captainshen.controller;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
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
    public String search(){
        return "search";
    }

    @RequestMapping(value = "/admin",method = {RequestMethod.GET})
    public String admin(){
        return "admin";
    }

}
