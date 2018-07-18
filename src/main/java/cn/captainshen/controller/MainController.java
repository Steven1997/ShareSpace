package cn.captainshen.controller;

import cn.captainshen.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;

@Controller
public class MainController {
    @GetMapping(value = {"/", "/index"})
    public String index(){
        return "index";
    }

    @GetMapping(value = {"/register"})
    public String register(){
        return "register";
    }

    @GetMapping(value = {"/login"})
    public String login(){
        return "login";
    }

    @GetMapping(value = {"/search"})
    public String search(){
        return "search";
    }

    @GetMapping(value = {"/admin"})
    public String manage(){
        return "admin";
    }
}