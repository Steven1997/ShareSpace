package cn.captainshen.controller;

import cn.captainshen.entity.User;
import cn.captainshen.service.GroupService;
import cn.captainshen.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@Controller
@SessionAttributes(value = {"loginUser"})
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private GroupService groupService;

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
            model.addAttribute("error_msg","登录成功！");
            return "index";

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
                           HttpSession session) throws IOException {
        if(userService.checkUsername(username)){
            model.addAttribute("error_msg","用户名已被注册，请重新注册！");
            return "register";
        }
        User user = new User();
        user.setUsername(username);
        user.setUsersex(usersex);
        user.setUserpwd(password);
        user.setGrade(Integer.valueOf(50)); //原始积分为50
        userService.addUser(user);
        user = userService.doLogin(username,password);
        session.setAttribute("loginUser", user);
        model.addAttribute("error_msg","恭喜您，注册成功！");
        return "index";
    }

    /**
     * 根据用户名模糊查询
     * @param username
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/findLikeUsers", method = {RequestMethod.POST})
    public String findLikeUsers(@RequestParam("username") String username, RedirectAttributes redirectAttributes){
        List<User> userList = userService.findLikeUsers(username);
        redirectAttributes.addFlashAttribute("userList",userList);
        return "redirect:/search?op=2";
    }
}
