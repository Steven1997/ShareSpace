package cn.captainshen.service.impl;
import cn.captainshen.dao.UserDao;
import cn.captainshen.entity.User;
import cn.captainshen.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

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
        return userDao.selectUser(username,null) != null;
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public User findUserByName(String username) {
        return userDao.selectUser(username,null);
    }

    @Override
    public List<User> findLikeUsers(String username) {
        return userDao.findLikeUsers(username);
    }

    @Override
    public List<User> findUsersByGroupId(String groupid) {
          return userDao.findUsersByGroupId(Integer.valueOf(groupid));
    }
}