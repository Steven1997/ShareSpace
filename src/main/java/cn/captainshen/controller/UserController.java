package cn.captainshen.controller;

import cn.captainshen.entity.User;
import cn.captainshen.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
@SessionAttributes(value = {"loginUser"})
public class UserController {
    @Resource
    private UserServiceImpl userService;

    /**
     * 用户登录
     * @param username
     * @param userpwd
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/doLogin", method = {RequestMethod.POST})
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String userpwd,
                        Model model) throws IOException {
        User loginUser = userService.doLogin(username, userpwd);
        if (loginUser != null) {
            model.addAttribute("loginUser",loginUser);
            return "redirect:/index";
        }
        model.addAttribute("error_msg","用户名或密码错误，请重新登录！");
        return "login";
    }

    /**
     * 用户注册
     * @param
     * @return
     */
    @RequestMapping(value = "/doRegister", method = {RequestMethod.POST})
    public String register(@RequestParam("username") String username, @RequestParam("usersex") String usersex,
                           @RequestParam("password") String password, Model model,
                           HttpSession session) {
        if(userService.checkUsername(username)){
            model.addAttribute("error_msg","用户名已被注册，请重新注册！");
            return "register";
        }
        User user = new User();
        user.setUsername(username);
        user.setUsersex(usersex);
        user.setUserpwd(password);
        userService.addUser(user);
        return "redirect:/index";
    }
}
