package tr.anil.questapp.response;

import lombok.Data;
import tr.anil.questapp.entity.Follow;

@Data
public class FollowResponse {

    Long id;
    Long followerId;
    Long followedId;
    String followerUsername;
    String followedUsername;

    public FollowResponse(Follow follow) {
        this.id = follow.getId();
        this.followerId = follow.getFollowerUser().getId();
        this.followerUsername = follow.getFollowerUser().getUsername();
        this.followedId = follow.getFollowedUser().getId();
        this.followedUsername = follow.getFollowedUser().getUsername();
    }
}
