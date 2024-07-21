package tr.anil.questapp.service;

import org.springframework.stereotype.Service;
import tr.anil.questapp.dao.UserDao;
import tr.anil.questapp.entity.User;

import java.util.List;

@Service
public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public User saveUser(User user) {
        return userDao.save(user);
    }

    public User getUser(Long userId) {
        return userDao.findById(userId).orElse(null);
    }

    public User updateUser(Long userId, User user) {
        User userOld = this.getUser(userId);
        if (userOld == null) {
            userOld.setUsername(user.getUsername());
            userOld.setPassword(user.getUsername());
            return userDao.save(userOld);
        }
        return null;
    }

    public void deleteUser(Long userId) {
        userDao.deleteById(userId);
    }
}
