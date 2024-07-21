package tr.anil.questapp.service;

import org.springframework.stereotype.Service;
import tr.anil.questapp.dao.LikeDao;
import tr.anil.questapp.entity.Like;
import tr.anil.questapp.entity.Post;
import tr.anil.questapp.entity.User;


import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    private LikeDao likeDao;
    private UserService userService;
    private PostService postService;

    public LikeService(LikeDao likeDao, UserService userService, PostService postService) {
        this.likeDao = likeDao;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Like> getAllLike(Optional<Long> userId, Optional<Long> postId) {
        if (userId.isPresent() && postId.isPresent())
            return likeDao.findByUserIdAndPostId(userId.get(),postId.get());
        if (userId.isPresent())
            return likeDao.findByUserId(userId.get());
        if (postId.isPresent())
            return likeDao.findByPostId(postId.get());
        return likeDao.findAll();
    }

    public Like getLikeById(Long likeId) {
        return likeDao.findById(likeId).orElse(null);
    }

    public Like save(Long userId, Long postId) {
        User user = userService.getUser(userId);
        if (user == null)
            return null;
        Post post = postService.getPostById(postId);
        if (post == null)
            return null;
        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        return likeDao.save(like);
    }

    public void deleteById(Long likeId) {
        likeDao.deleteById(likeId);
    }
}
