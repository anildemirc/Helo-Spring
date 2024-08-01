package tr.anil.questapp.response;

import lombok.Data;
import tr.anil.questapp.entity.User;

@Data
public class    UserResponse {

    Long id;
    int avatarId;
    String username;
    int countFollowed;
    int countFollower;

    public UserResponse(User user) {
        this.id = user.getId();
        this.avatarId = user.getAvatar();
        this.username = user.getUsername();
        this.countFollowed = 0;
        this.countFollower = 0;
    }

    public UserResponse(User user, int countFollowed, int countFollower) {
        this.id = user.getId();
        this.avatarId = user.getAvatar();
        this.username = user.getUsername();
        this.countFollowed = countFollowed;
        this.countFollower = countFollower;
    }
}
