package tr.anil.questapp.request;

import lombok.Data;

@Data
public class LikeCreateRequest {

    Long userId;
    Long postId;
}
