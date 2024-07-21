package tr.anil.questapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.anil.questapp.entity.Post;

import java.util.List;

public interface PostDao extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
}
