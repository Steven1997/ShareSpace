package cn.captainshen.dao;

import cn.captainshen.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface UserDao {

    User selectUser(@Param("username") String username,@Param("userpwd") String userpwd);

    User checkUsername(String username);

    void addUser(User user);
}
