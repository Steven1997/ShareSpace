package cn.captainshen.dao;

import cn.captainshen.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserDao {

    User selectUser(@Param("username") String username,@Param("userpwd") String userpwd);

    User checkUsername(String username);

    User findUserByUserId(int userId);

    void addUser(User user);

    List<Integer> findGroupIdsByUserId(Integer userId);

    List<User> findUsersByGroupId(Integer groupid);

    List<User> findLikeUsers(String username);
}
