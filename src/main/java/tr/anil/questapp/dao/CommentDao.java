package tr.anil.questapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.anil.questapp.entity.Comment;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment,Long> {

    List<Comment> findByUserIdAndPostId(Long userId, Long postId);

    List<Comment> findAllByUserId(Long userId);

    List<Comment> findAllByPostId(Long postId);
}
