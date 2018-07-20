package cn.captainshen.dao;

import cn.captainshen.entity.Group;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupDao {
        public void createGroup(@Param("userid") Integer userid, @Param("groupname") String groupname,
                                @Param("grouppwd") String grouppwd, @Param("groupdesc") String groupdesc);

        public void addGroupMember(@Param("groupid") Integer groupid, @Param("userid") Integer userid,
                                   @Param("username") String username,@Param("groupname") String groupname);

        public Group selectGroup(@Param("userid")Integer userid,@Param("groupname")String groupname);

        public List<Group> findAllCreateGroup(Integer userid);

        public List<Group> findAllJoinGroup(Integer userid);

        public Integer selectGroupId(@Param("userid") Integer userid,@Param("groupname") String groupname);

        public Group selectGroupById(Integer groupid);
}
