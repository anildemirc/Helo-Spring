package tr.anil.questapp.service;

import org.springframework.stereotype.Service;
import tr.anil.questapp.dao.FollowDao;
import tr.anil.questapp.entity.Follow;
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

    public FollowService(FollowDao followDao, UserService userService) {
        this.followDao = followDao;
        this.userService = userService;
    }

    public List<UserResponse> getFollowListByFollowedUserId(Long userId) {
        List<Follow> followList = followDao.findAllByFollowedUserId(userId);
        return followList.stream().map(p -> new UserResponse(p.getFollowerUser())).collect(Collectors.toList());
    }

    public List<UserResponse> getFollowListByFollowerUserId(Long userId) {
        List<Follow> followList = followDao.findAllByFollowerUserId(userId);
        return followList.stream().map(p -> new UserResponse(p.getFollowedUser())).collect(Collectors.toList());
    }

    public FollowResponse save(FollowRequest followRequest) {
        if (followDao.findByFollowedUserIdAndFollowerUserId(followRequest.getFollowedId(), followRequest.getFollowerId()) != null)
            throw new SystemException("Kullanıcı takip etme işleminde hata");

        Follow follow = new Follow();
        follow.setFollowerUser(userService.getUser(followRequest.getFollowerId()));
        follow.setFollowedUser(userService.getUser(followRequest.getFollowedId()));
        return new FollowResponse(followDao.save(follow));
    }

    public int getFollowedCountByUserId(Long userId) {
        return followDao.getFollowedCountByUserId(userId);
    }
    public int getFollowerCountByUserId(Long userId) {
        return followDao.getFollowerCountByUserId(userId);
    }

    public void deleteById(Long followId) {
        followDao.deleteById(followId);
    }

    public FollowResponse getFollow(Long followerId, Long followedId) {
        Follow follow = followDao.findByFollowedUserIdAndFollowerUserId(followedId, followerId);
        if (follow == null)
            return null;
        return new FollowResponse(follow);
    }
}
