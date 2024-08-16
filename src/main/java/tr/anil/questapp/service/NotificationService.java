package tr.anil.questapp.service;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import tr.anil.questapp.dao.NotificationDao;
import tr.anil.questapp.entity.Notification;
import tr.anil.questapp.enums.NotificationTypes;
import tr.anil.questapp.exception.UserNotFoundException;
import tr.anil.questapp.response.NotificationResponse;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private NotificationDao notificationDao;
    private UserService userService;
    private PostService postService;

    public NotificationService(NotificationDao notificationDao, @Lazy UserService userService, @Lazy PostService postService) {
        this.notificationDao = notificationDao;
        this.userService = userService;
        this.postService = postService;
    }

    @Transactional
    public List<Notification> getAllNotificationByUserId(Long userId) {
        if (userService.getUser(userId) == null)
            throw new UserNotFoundException("User not found");
        List<Notification> list = notificationDao.findAllByToUserId(userId);
        return list;
    }


    public Notification saveNotification(Long userId, Long toUserId, Long postId, Long eventId, int type) {
        try {
            if (userId == toUserId)
                throw new Exception("Not be same user in notification");
            Notification notification = new Notification();
            notification.setUser(userService.getUser(userId));
            notification.setToUser(userService.getUser(toUserId));
            notification.setType(NotificationTypes.getType(type));
            notification.setPost(postId != null ? postService.getPostById(postId) : null);
            notification.setEventId(eventId);
            return notificationDao.save(notification);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteNotificationById(Long notificationId) {
        notificationDao.deleteById(notificationId);
    }
}
