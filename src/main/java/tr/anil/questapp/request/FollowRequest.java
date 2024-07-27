package tr.anil.questapp.request;

import lombok.Data;

@Data
public class FollowRequest {

    Long followerId;
    Long followedId;
}
