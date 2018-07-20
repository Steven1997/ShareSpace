package cn.captainshen.dao;

import cn.captainshen.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserDao {

    User selectUser(@Param("username") String username,@Param("userpwd") String userpwd);

<<<<<<< HEAD
    User checkUsername(String username);

    User findUserByUserId(int userId);

=======
>>>>>>> 220adc6f366101e32cce82d6ca708f9107ae20b1
    void addUser(User user);

    List<Integer> findGroupIdsByUserId(int userId);
}
