package tr.anil.questapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.anil.questapp.response.NotificationResponse;
import tr.anil.questapp.service.NotificationService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAllNotificationByUserId(@RequestParam(value = "userId") Long userId) {
        return new ResponseEntity<>(notificationService.getAllNotificationByUserId(userId).stream().map(p -> new NotificationResponse(p)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @DeleteMapping("/{notificationId}")
    public void deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotificationById(notificationId);
    }


}