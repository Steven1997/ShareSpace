package cn.captainshen.service;

import cn.captainshen.entity.LocalFile;
import cn.captainshen.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    /**
     * 用户登录
     * @param username 用户名
     * @param userpwd 用户密码
     * @return 登录成功返回User对象，失败返回null
     * @throws IOException
     */
    User doLogin(String username, String userpwd) throws IOException;

    /**
     * 校验用户名是否存在
     * @param username 用户名
     * @return 用户名已存在返回true，否则返回false
     */
    boolean checkUsername(String username);

    /**
     * 注册用户
     * @param user
     */
    void addUser(User user);

    /**
     * 根据用户名精确查找用户
     * @param username
     * @return
     */
    User findUserByName(String username);

    /**
     * 根据用户名模糊查找用户
     * @param username
     * @return
     */
    List<User> findLikeUsers(String username);

    /**
     * 根据群组编号查询该群组的所有成员
     * @param groupid
     * @return
     */
    List<User> findUsersByGroupId(String groupid);
}
