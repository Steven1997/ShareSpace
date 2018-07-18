package cn.captainshen.service;
import cn.captainshen.dao.UserDao;
import cn.captainshen.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class UserService {
    @Resource
    private UserDao userDao;

    /**
     * 用户登录
     * @param username 用户名
     * @param userpwd 用户密码
     * @return 登录成功返回User对象，失败返回null
     * @throws IOException
     */
    public User doLogin(String username, String userpwd) throws IOException {
        return userDao.selectUser(username,userpwd);
    }

    /**
     * 校验用户名是否存在
     * @param username 用户名
     * @return 用户名已存在返回true，否则返回false
     */
    public boolean checkUsername(String username) {
        return userDao.checkUsername(username) != null;
    }

    /**
     * 注册用户
     * @param user
     */
    public void addUser(User user) {
        userDao.addUser(user);
    }
}
