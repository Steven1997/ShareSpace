package cn.captainshen.service;

import cn.captainshen.entity.Group;

import java.util.List;

public interface GroupService {
    public void createGroup(String userid, String groupname, String grouppwd,String groupdesc);

    public void addGroupMember(String groupid,String userid,String username,String groupname);

    public Group selectGroup(String userid,String groupname);

    public List<Group> findAllCreateGroup(String userid);

    public List<Group> findAllJoinGroup(String userid);

    public Group selectGroupById(String groupid);

    public boolean isInGroup(String userid,String groupid);

    public void deleteMemberOrLeaveGroup(String groupid,String memberid);
}
