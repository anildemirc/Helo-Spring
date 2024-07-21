package tr.anil.questapp.response;

import lombok.Data;
import tr.anil.questapp.entity.Post;

import java.util.Date;

@Data
public class PostResponse {
    Long id;
    Long userId;
    String username;
    String title;
    String text;
    Date createTime;

    public PostResponse(Post entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.username = entity.getUser().getUsername();
        this.title = entity.getTitle();
        this.text = entity.getText();
        this.createTime = new Date();
    }
}
