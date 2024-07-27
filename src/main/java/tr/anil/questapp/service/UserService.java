package tr.anil.questapp.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import tr.anil.questapp.dao.CommentDao;
import tr.anil.questapp.dao.LikeDao;
import tr.anil.questapp.dao.PostDao;
import tr.anil.questapp.dao.UserDao;
import tr.anil.questapp.entity.Comment;
import tr.anil.questapp.entity.Like;
import tr.anil.questapp.entity.User;
import tr.anil.questapp.response.UserResponse;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserDao userDao;
    private LikeDao likeDao;
    private CommentDao commentDao;
    private PostDao postDao;

    public UserService(UserDao userDao, LikeDao likeDao, CommentDao commentDao, PostDao postDao) {
        this.userDao = userDao;
        this.likeDao = likeDao;
        this.commentDao = commentDao;
        this.postDao = postDao;
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
        if (userOld != null) {
            if ( user.getUsername() != null)
                userOld.setUsername(user.getUsername());
            if (user.getPassword() != null)
                userOld.setPassword(user.getUsername());
            if (user.getAvatar() != 0)
                userOld.setAvatar(user.getAvatar());
            return userDao.save(userOld);
        }
        return null;
    }

    public void deleteUser(Long userId) {
        userDao.deleteById(userId);
    }

    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public List<Object> getActivityByUser(Long userId) {
        List<Long> postIds = postDao.findTopByUserId(userId);
        if (postIds.isEmpty())
            return null;
        List<Object> comments = commentDao.findUserCommentsByPostId(postIds);
        List<Object> likes = likeDao.findUserLikesByPostId(postIds);
        List<Object> result = new ArrayList<>();
        result.addAll(comments);
        result.addAll(likes);
        return result;
    }
}
