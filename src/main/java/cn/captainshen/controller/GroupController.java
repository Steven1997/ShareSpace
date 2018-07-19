package cn.captainshen.controller;

import cn.captainshen.entity.Group;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GroupController {
    @RequestMapping(value = "/doCreateGroup",method = {RequestMethod.POST})
    public String doCreateGroup(Group group){
        return "";
    }
}
