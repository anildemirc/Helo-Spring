package tr.anil.questapp.request;

import lombok.Data;

@Data
public class RefreshRequest {

    Long userId;
    String refreshToken;

}
