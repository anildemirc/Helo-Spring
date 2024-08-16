package tr.anil.questapp.response;

import lombok.Data;
import tr.anil.questapp.entity.Like;
import tr.anil.questapp.entity.Post;

import java.util.Date;
import java.util.List;

@Data
public class PostResponse {
    Long id;
    Long userId;
    String username;
    int userFollowerCount;
    int userFollowingCount;
    String title;
    String text;
    Date createTime;
    int postLikeCount;

    public PostResponse(Post entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.username = entity.getUser().getUsername();
        this.title = entity.getTitle();
        this.text = entity.getText();
        this.createTime = entity.getCreateDate();
        this.postLikeCount = entity.getCountLike();
    }

    public PostResponse(Post entity, int userFollowerCount, int userFollowingCount) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.username = entity.getUser().getUsername();
        this.title = entity.getTitle();
        this.text = entity.getText();
        this.createTime = entity.getCreateDate();
        this.postLikeCount = entity.getCountLike();
        this.userFollowingCount = userFollowingCount;
        this.userFollowerCount = userFollowerCount;
    }
}
