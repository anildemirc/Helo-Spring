package tr.anil.questapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tr.anil.questapp.entity.Follow;

import java.util.List;

public interface FollowDao extends JpaRepository<Follow, Long> {
    List<Follow> findAllByFollowedUserId(Long userId);
    List<Follow> findAllByFollowerUserId(Long userId);


    @Query(value = "select * from _follow where followed_user_id = :followedId and follower_user_id = :followerId", nativeQuery = true)
    Follow findByFollowedUserIdAndFollowerUserId(@Param("followedId") Long followedId, @Param("followerId") Long followerId);

    @Query(value = "select count(*) from _follow where followed_user_id = :userId", nativeQuery = true)
    Long getFollowedCountByUserId(@Param("userId") Long userId);

    @Query(value = "select count(*) from _follow where follower_user_id = :userId", nativeQuery = true)
    Long getFollowerCountByUserId(@Param("userId") Long userId);
}
