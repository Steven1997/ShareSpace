package cn.captainshen.service.impl;
import cn.captainshen.dao.UserDao;
import cn.captainshen.entity.User;
import cn.captainshen.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User doLogin(String username, String userpwd) throws IOException {
        return userDao.selectUser(username,userpwd);
    }

    @Override
    public boolean checkUsername(String username) {
        return userDao.checkUsername(username) != null;
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }
}