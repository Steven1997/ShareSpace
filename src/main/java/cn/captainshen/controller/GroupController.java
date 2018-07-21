package cn.captainshen.controller;

import cn.captainshen.entity.Group;
import cn.captainshen.entity.LocalFile;
import cn.captainshen.entity.User;
import cn.captainshen.service.FileService;
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

    @Resource
    FileService fileService;
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
        User loginUser = (User)session.getAttribute("loginUser");
        g = groupService.selectGroup(loginUser.getUserid().toString(), (String) mp.get("group"));
        if(g == null){
            data.put("msg","邀请失败，请保证您创建了该群组！");
            return data.toString();
        }
        for (Map.Entry<String, Object> entry : mp.entrySet()) {
            if(entry.getKey().equals("group"))
                continue;
            else{
                User member = userService.findUserByName((String) entry.getValue());

                if (member == null) {
                    data.put("msg","邀请失败，请保证所有被邀请的用户存在！");
                    return data.toString();
                } else if (groupService.isInGroup(member.getUserid().toString(), g.getGroupid().toString())) {
                    data.put("msg","邀请失败，邀请了已在群组中的用户！");
                    return data.toString();
                } else {
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


    /**
     * 根据群组编号查询该组的所有成员、文件
     * @param groupid
     * @return
     */
    @RequestMapping(value = "/groupDetail/{groupid}",method = {RequestMethod.GET})
    public String findUsersByGroupId(@PathVariable("groupid") String groupid,Model model,
                                     HttpSession session, RedirectAttributes redirectAttributes){
        List<User> memberList = userService.findUsersByGroupId(groupid);
        // 判断查询用户是否在该组中
        User user = (User)session.getAttribute("loginUser");
        Boolean checkable = false;
        for(int i = 0; i < memberList.size(); ++i){
            if(user.getUserid() == memberList.get(i).getUserid()){
                checkable = true;
                break;
            }
        }
        if(!checkable){
            redirectAttributes.addFlashAttribute("error_msg", "权限不足");
            return "redirect:/search?op=2";
        }
        String groupname = groupService.selectGroupById(groupid).getGroupname();
        model.addAttribute("memberList",memberList);
        model.addAttribute("groupname",groupname);

        // 查询该组所有文件
        List<LocalFile> groupFileList = fileService.findGroupFilesByGroupId(Integer.valueOf(groupid));
        model.addAttribute("groupFileList", groupFileList);
        return "display";
    }
}
