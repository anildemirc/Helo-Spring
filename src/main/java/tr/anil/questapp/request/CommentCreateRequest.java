package tr.anil.questapp.request;

import lombok.Data;

@Data
public class CommentCreateRequest {

    String text;
    Long userId;
    Long postId;

}
