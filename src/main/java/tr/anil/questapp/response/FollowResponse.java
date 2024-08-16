package tr.anil.questapp.response;

import lombok.Data;
import tr.anil.questapp.entity.Follow;

@Data
public class FollowResponse {

    Long id;
    Long followerId;
    Long followingId;
    String followerUsername;
    String followingUsername;

    public FollowResponse(Follow follow) {
        this.id = follow.getId();
        this.followerId = follow.getFollowerUser().getId();
        this.followerUsername = follow.getFollowerUser().getUsername();
        this.followingId = follow.getFollowingUser().getId();
        this.followingUsername = follow.getFollowingUser().getUsername();
    }
}
