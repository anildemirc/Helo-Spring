package tr.anil.questapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.anil.questapp.entity.Notification;

import java.util.List;

public interface NotificationDao extends JpaRepository<Notification, Long> {
    List<Notification> findAllByToUserId(Long userId);
}
