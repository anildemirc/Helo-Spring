package tr.anil.questapp.response;

import lombok.Data;
import tr.anil.questapp.entity.User;

@Data
public class    UserResponse {

    Long id;
    int avatarId;
    String username;
    Long countFollowed;

    public UserResponse(User user) {
        this.id = user.getId();
        this.avatarId = user.getAvatar();
        this.username = user.getUsername();
        this.countFollowed = 0L;
    }

    public UserResponse(User user, Long countFollowed) {
        this.id = user.getId();
        this.avatarId = user.getAvatar();
        this.username = user.getUsername();
        this.countFollowed = countFollowed;
    }
}
