package tr.anil.questapp.request;

import lombok.Data;

@Data
public class PostCreateRequest {
    String text;
    String title;
    Long userId;
}