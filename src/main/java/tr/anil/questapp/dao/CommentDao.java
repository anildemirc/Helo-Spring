package tr.anil.questapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tr.anil.questapp.entity.Comment;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment,Long> {

    List<Comment> findByUserIdAndPostId(Long userId, Long postId);

    List<Comment> findAllByUserId(Long userId);

    List<Comment> findAllByPostId(Long postId);

    @Query(value = "select 'commented on', c.post_id, u.avatar, u.username from _comment c left join _user u " +
            "on c.user_id = u.id where c.post_id in :postIds limit 5"
            , nativeQuery = true)
    List<Object> findUserCommentsByPostId(@Param("postIds") List<Long> postIds);

}
