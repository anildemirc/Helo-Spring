package tr.anil.questapp.response;

import lombok.Data;
import tr.anil.questapp.entity.User;

@Data
public class    UserResponse {

    Long id;
    int avatarId;
    String username;
    int countFollowing;
    int countFollower;

    public UserResponse(User user) {
        this.id = user.getId();
        this.avatarId = user.getAvatar();
        this.username = user.getUsername();
        this.countFollowing = 0;
        this.countFollower = 0;
    }

    public UserResponse(User user, int countFollowed, int countFollower) {
        this.id = user.getId();
        this.avatarId = user.getAvatar();
        this.username = user.getUsername();
        this.countFollowing = countFollowed;
        this.countFollower = countFollower;
    }
}
