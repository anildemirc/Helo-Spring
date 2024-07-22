package tr.anil.questapp.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import tr.anil.questapp.dao.LikeDao;
import tr.anil.questapp.entity.Like;
import tr.anil.questapp.entity.Post;
import tr.anil.questapp.entity.User;
import tr.anil.questapp.request.LikeCreateRequest;
import tr.anil.questapp.response.LikeResponse;
import tr.anil.questapp.response.PostResponse;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private LikeDao likeDao;
    private UserService userService;
    private PostService postService;

    public LikeService(LikeDao likeDao, UserService userService, @Lazy PostService postService) {
        this.likeDao = likeDao;
        this.userService = userService;
        this.postService = postService;
    }

    public List<LikeResponse> getAllLike(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if (userId.isPresent() && postId.isPresent())
            list = likeDao.findByUserIdAndPostId(userId.get(),postId.get());
        else if (userId.isPresent())
            list = likeDao.findByUserId(userId.get());
        else if (postId.isPresent())
            list = likeDao.findByPostId(postId.get());
        else
            list = likeDao.findAll();
        return list.stream().map(p -> new LikeResponse(p)).collect(Collectors.toList());
    }

    public Like getLikeById(Long likeId) {
        return likeDao.findById(likeId).orElse(null);
    }

    public LikeResponse save(LikeCreateRequest likeCreateRequest) {
        User user = userService.getUser(likeCreateRequest.getUserId());
        if (user == null)
            return null;
        Post post = postService.getPostById(likeCreateRequest.getPostId());
        if (post == null)
            return null;
        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        return new LikeResponse(likeDao.save(like));
    }

    public void deleteById(Long likeId) {
        likeDao.deleteById(likeId);
    }
}
