package cn.captainshen.service;

import cn.captainshen.entity.Group;

import java.util.List;

public interface GroupService {
    public void createGroup(String userid, String groupname, String grouppwd,String groupdesc);

    public void addGroupMember(String groupid,String userid,String username,String groupname);

    public Group checkGroup(String userid,String groupname);

    public List<Group> findAllCreateGroup(String userid);

    public List<Group> findAllJoinGroup(String userid);

    public Integer selectGroupId(String userid,String groupname);
}
