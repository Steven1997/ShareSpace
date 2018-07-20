package cn.captainshen.dao;

import cn.captainshen.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserDao {

    User selectUser(@Param("username") String username,@Param("userpwd") String userpwd);

    void addUser(User user);

    List<Integer> findGroupIdsByUserId(int userId);
}
