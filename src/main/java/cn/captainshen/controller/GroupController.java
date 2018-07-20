package cn.captainshen.controller;

import cn.captainshen.entity.Group;
import cn.captainshen.entity.User;
import cn.captainshen.service.GroupService;
import cn.captainshen.service.UserService;
import org.json.JSONObject;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class GroupController {
    @Resource
    GroupService groupService;

    @Resource
    UserService userService;
    /**
     * 创建群组
     *
     * @param userid
     * @param groupname
     * @param grouppwd
     * @param groupdesc
     * @param redirectAttributes
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/doCreateGroup", method = {RequestMethod.POST})
    public String doCreateGroup(@RequestParam("userid") String userid, @RequestParam("group_name") String groupname,
                                @RequestParam("pwd") String grouppwd, @RequestParam("desc") String groupdesc,
                                RedirectAttributes redirectAttributes, HttpSession httpSession) {
        Group query = groupService.selectGroup(userid, groupname);

        if (query == null) {
            groupService.createGroup(userid, groupname, grouppwd, groupdesc);
            User user = (User) httpSession.getAttribute("loginUser");
            Integer groupnum = groupService.selectGroup(userid,groupname).getGroupid();
            groupService.addGroupMember(groupnum.toString(), user.getUserid().toString(), user.getUsername(), groupname);
            redirectAttributes.addFlashAttribute("error_msg", "群组创建成功！");
            return "redirect:/search?op=2";
        } else {
            redirectAttributes.addFlashAttribute("error_msg", "您已创建过同名群组，请重新创建！");
            return "redirect:/search?op=2&ct=1";
        }


    }


    @RequestMapping(value = "/addGroupMember",method = {RequestMethod.POST},produces = "application/json; charset=utf-8")
    @ResponseBody
    public String addGroupMember(@RequestBody String json,HttpSession session){
        Group g = null;
        JSONObject jsonObject = new JSONObject(json);
        JSONObject data = new JSONObject();
        Map<String,Object> mp = jsonObject.toMap();
        List<User> inviteList = new ArrayList<User>();
        for (Map.Entry<String, Object> entry : mp.entrySet()) {
            if(entry.getKey().equals("group")){
                User loginUser = (User)session.getAttribute("loginUser");
                g = groupService.selectGroup(loginUser.getUserid().toString(), (String) entry.getValue());
                if(g == null){
                    data.put("msg","邀请失败，请保证您创建了该群组！");
                    return data.toString();
                }
            }
            else{
                User member = userService.findUserByName((String) entry.getValue());
                System.out.println(member);
                if (member == null) {
                    data.put("msg","邀请失败，请保证所有被邀请的用户存在！");
                    return data.toString();
                }
                else{
                    inviteList.add(member);
                }
            }

        }
        for (User user : inviteList) {
            groupService.addGroupMember(g.getGroupid().toString(),user.getUserid().toString(),user.getUsername(),g.getGroupname());
        }
        data.put("msg","邀请成功！");
        return data.toString();
    }



}
