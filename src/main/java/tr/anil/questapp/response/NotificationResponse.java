package tr.anil.questapp.response;

import lombok.Data;
import tr.anil.questapp.entity.Notification;

@Data
public class NotificationResponse {

    Long id;

    Long userId;
    Long toUserId;
    Long postId;
    Long eventId;

    String username;
    String postName;

    int type;

    public NotificationResponse(Notification notification) {
        this.id = notification.getId();
        this.userId = notification.getUser().getId();
        this.username = notification.getUser().getUsername();
        this.toUserId = notification.getToUser().getId();
        this.postId = notification.getPost() != null ? notification.getPost().getId() : null;
        this.postName = notification.getPost() != null ? notification.getPost().getTitle() : null;
        this.eventId = notification.getEventId();
        this.type = notification.getType().getNotificationTypes();
    }
}
