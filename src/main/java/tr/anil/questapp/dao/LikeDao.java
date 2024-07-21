package tr.anil.questapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.anil.questapp.entity.Like;

import java.util.List;

public interface LikeDao extends JpaRepository<Like, Long> {
    List<Like> findByUserIdAndPostId(Long userId, Long postId);

    List<Like> findByUserId(Long userId);

    List<Like> findByPostId(Long postId);
}
