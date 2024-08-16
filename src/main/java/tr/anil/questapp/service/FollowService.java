package tr.anil.questapp.service;

import org.springframework.stereotype.Service;
import tr.anil.questapp.dao.FollowDao;
import tr.anil.questapp.entity.Follow;
import tr.anil.questapp.entity.User;
import tr.anil.questapp.enums.NotificationTypes;
import tr.anil.questapp.exception.SystemException;
import tr.anil.questapp.request.FollowRequest;
import tr.anil.questapp.response.FollowResponse;
import tr.anil.questapp.response.LikeResponse;
import tr.anil.questapp.response.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowService {

    private FollowDao followDao;
    private UserService userService;
    private NotificationService notificationService;

    public FollowService(FollowDao followDao, UserService userService, NotificationService notificationService) {
        this.followDao = followDao;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    public List<User> getFollowListByFollowingUserId(Long userId) {
        List<Follow> followList = followDao.findAllByFollowingUserId(userId);
        return followList.stream().map(p -> p.getFollowerUser()).collect(Collectors.toList());
    }

    public List<User> getFollowListByFollowerUserId(Long userId) {
        List<Follow> followList = followDao.findAllByFollowerUserId(userId);
        return followList.stream().map(p -> p.getFollowingUser()).collect(Collectors.toList());
    }

    public Follow save(FollowRequest followRequest) {
        if (followDao.findByFollowedUserIdAndFollowerUserId(followRequest.getFollowingId(), followRequest.getFollowerId()) != null)
            throw new SystemException("Kullanıcı takip etme işleminde hata");

        Follow follow = new Follow();
        follow.setFollowerUser(userService.getUser(followRequest.getFollowerId()));
        follow.setFollowingUser(userService.getUser(followRequest.getFollowingId()));
        follow = followDao.save(follow);
        userService.addFollow(follow);
        notificationService.saveNotification(followRequest.getFollowerId(), followRequest.getFollowingId(), null, follow.getId(), NotificationTypes.FOLLOW.getNotificationTypes());
        return follow;
    }

    public void deleteById(Long followId) {
        userService.deleteFollow(getFollowById(followId));
        followDao.deleteById(followId);
    }

    public Follow getFollowById(Long id) {
        Follow follow = followDao.findById(id).orElse(null);
        if (follow == null)
            return null;
        return follow;
    }

    public Follow getFollow(Long userId, Long followingId) {
        Follow follow = followDao.findByFollowedUserIdAndFollowerUserId(userId, followingId);
        if (follow == null)
            return null;
        return follow;
    }
}
