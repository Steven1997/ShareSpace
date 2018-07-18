package cn.captainshen.dao;

import cn.captainshen.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface UserDao {

    public User selectUser(@Param("username") String username,@Param("userpwd") String userpwd);

    public User checkUsername(String username);

    public void addUser(User user);

}
