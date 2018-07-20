package cn.captainshen.service.impl;

import cn.captainshen.dao.GroupDao;
import cn.captainshen.entity.Group;
import cn.captainshen.service.GroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    @Resource
    private GroupDao groupDao;

    @Override
    public void createGroup(String userid, String groupname, String grouppwd, String groupdesc) {
        groupDao.createGroup(Integer.valueOf(userid),groupname,grouppwd,groupdesc);
    }

    /**
     * 添加群组成员
     * @param groupid
     * @param userid
     * @param username
     * @param groupname
     */
    @Override
    public void addGroupMember(String groupid, String userid, String username, String groupname) {
        groupDao.addGroupMember(Integer.valueOf(groupid),Integer.valueOf(userid),username,groupname);
    }

    /**
     * 检查某个用户是否已经创建了某名称的群组
     * @param userid
     * @param groupname
     * @return
     */
    @Override
    public Group selectGroup(String userid, String groupname) {
        return groupDao.selectGroup(Integer.valueOf(userid),groupname);
    }



    /**
     * 查询某用户创建的所有群组
     * @param userid
     * @return
     */
    @Override
    public List<Group> findAllCreateGroup(String userid) {
        return groupDao.findAllCreateGroup(Integer.valueOf(userid));
    }

    /**
     * 查询某用户加入的所有群组
     * @param userid
     * @return
     */
    @Override
    public List<Group> findAllJoinGroup(String userid) {
        return groupDao.findAllJoinGroup(Integer.valueOf(userid));
    }


    @Override
    public Group selectGroupById(String groupid) {
        return groupDao.selectGroupById(Integer.valueOf(groupid));
    }
}
