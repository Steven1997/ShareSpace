package cn.captainshen.controller;

import cn.captainshen.entity.Group;
import cn.captainshen.entity.User;
import cn.captainshen.service.GroupService;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
public class GroupController {
    @Resource
    GroupService groupService;

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
                                @RequestParam("pwd") String grouppwd, @RequestParam("desc") String groupdesc, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        Group query = groupService.checkGroup(userid, groupname);

        if (query == null) {
            groupService.createGroup(userid, groupname, grouppwd, groupdesc);
            User user = (User) httpSession.getAttribute("loginUser");
            Integer groupnum = groupService.selectGroupId(userid, groupname);
            groupService.addGroupMember(groupnum.toString(), user.getUserid().toString(), user.getUsername(), groupname);
            redirectAttributes.addFlashAttribute("error_msg", "群组创建成功！");
            return "redirect:/search?op=2";
        } else {
            redirectAttributes.addFlashAttribute("error_msg", "您已创建过同名群组，请重新创建！");
            return "redirect:/search?op=2&ct=1";
        }


    }

}
