package tr.anil.questapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tr.anil.questapp.entity.Follow;

import java.util.List;

public interface FollowDao extends JpaRepository<Follow, Long> {
    List<Follow> findAllByFollowingUserId(Long userId);
    List<Follow> findAllByFollowerUserId(Long userId);


    @Query(value = "select * from _follow where follower_user_id = :userId and following_user_id = :followingId", nativeQuery = true)
    Follow findByFollowedUserIdAndFollowerUserId(@Param("userId") Long userId, @Param("followingId") Long followingId);

    @Query(value = "select count(*) from _follow where following_user_id = :userId", nativeQuery = true)
    int getFollowedCountByUserId(@Param("userId") Long userId);

    @Query(value = "select count(*) from _follow where follower_user_id = :userId", nativeQuery = true)
    int getFollowerCountByUserId(@Param("userId") Long userId);
}
