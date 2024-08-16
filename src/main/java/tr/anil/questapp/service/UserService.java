package tr.anil.questapp.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import tr.anil.questapp.dao.CommentDao;
import tr.anil.questapp.dao.LikeDao;
import tr.anil.questapp.dao.PostDao;
import tr.anil.questapp.dao.UserDao;
import tr.anil.questapp.entity.Comment;
import tr.anil.questapp.entity.Follow;
import tr.anil.questapp.entity.Like;
import tr.anil.questapp.entity.User;
import tr.anil.questapp.exception.FollowNotFoundException;
import tr.anil.questapp.exception.UserNotFoundException;
import tr.anil.questapp.response.FollowResponse;
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
    private FollowService followService;

    public UserService(UserDao userDao, LikeDao likeDao, CommentDao commentDao, PostDao postDao, @Lazy FollowService followService) {
        this.userDao = userDao;
        this.likeDao = likeDao;
        this.commentDao = commentDao;
        this.postDao = postDao;
        this.followService = followService;
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
            if (user.getUsername() != null)
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

    public void addFollow(Follow follow) {
        if (followService.getFollowById(follow.getId()) == null)
            throw new FollowNotFoundException();
        addFollower(follow.getFollowerUser());
        addFollowing(follow.getFollowingUser());
    }

    public void deleteFollow(Follow follow) {
        if (followService.getFollowById(follow.getId()) == null)
            throw new FollowNotFoundException();
        deleteFollower(follow.getFollowerUser());
        deleteFollowing(follow.getFollowingUser());
    }

    public void addFollowing(User user) {
        try {
            if (getUser(user.getId()) == null)
                throw new UserNotFoundException();
            if (user.getFollowingCount() < 0)
                throw new Exception("Kullanıcı following count 0 dan küçük olamaz");
            user.setFollowingCount(user.getFollowingCount()+1);
            userDao.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addFollower(User user) {
        try {
            if (getUser(user.getId()) == null)
                throw new UserNotFoundException();
            if (user.getFollowingCount() < 0)
                throw new Exception("Kullanıcı following count 0 dan küçük olamaz");
            user.setFollowerCount(user.getFollowerCount()+1);
            userDao.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFollowing(User user) {
        try {
            if (getUser(user.getId()) == null)
                throw new UserNotFoundException();
            if (user.getFollowingCount() < 1)
                throw new Exception("Kullanıcı following count 0 dan küçük olamaz");
            user.setFollowingCount(user.getFollowingCount()-1);
            userDao.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFollower(User user) {
        try {
            if (getUser(user.getId()) == null)
                throw new UserNotFoundException();
            if (user.getFollowerCount() < 1)
                throw new Exception("Kullanıcı following count 0 dan küçük olamaz");
            user.setFollowerCount(user.getFollowerCount()-1);
            userDao.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
