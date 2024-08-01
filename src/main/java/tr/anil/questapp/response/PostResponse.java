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
    int userFollowedCount;
    String title;
    String text;
    Date createTime;
    List<LikeResponse> postLikes;

    public PostResponse(Post entity, List<LikeResponse> likes) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.username = entity.getUser().getUsername();
        this.title = entity.getTitle();
        this.text = entity.getText();
        this.createTime = new Date();
        this.postLikes = likes;
    }

    public PostResponse(Post entity, List<LikeResponse> likes, int userFollowerCount, int userFollowedCount) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.username = entity.getUser().getUsername();
        this.title = entity.getTitle();
        this.text = entity.getText();
        this.createTime = new Date();
        this.postLikes = likes;
        this.userFollowedCount = userFollowedCount;
        this.userFollowerCount = userFollowerCount;
    }
}
