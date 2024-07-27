package tr.anil.questapp.response;

import lombok.Data;
import tr.anil.questapp.entity.Comment;

@Data
public class CommentResponse {

    Long id;
    Long userId;
    String text;
    String username;

    public CommentResponse(Comment comment) {
        this.userId = comment.getUser().getId();
        this.id = comment.getId();
        this.text = comment.getText();
        this.username = comment.getUser().getUsername();
    }
}
