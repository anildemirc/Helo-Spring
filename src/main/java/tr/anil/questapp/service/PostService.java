package tr.anil.questapp.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.anil.questapp.dao.PostDao;
import tr.anil.questapp.entity.Like;
import tr.anil.questapp.entity.Post;
import tr.anil.questapp.entity.User;
import tr.anil.questapp.exception.PostNotFoundException;
import tr.anil.questapp.request.PostCreateRequest;
import tr.anil.questapp.request.PostUpdateRequest;
import tr.anil.questapp.response.LikeResponse;
import tr.anil.questapp.response.PostResponse;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostDao postDao;
    private UserService userService;
    private LikeService likeService;
    private FollowService followService;

    public PostService(PostDao postDao, UserService userService, LikeService likeService, FollowService followService) {
        this.postDao = postDao;
        this.userService = userService;
        this.likeService = likeService;
        this.followService = followService;
    }

    @Transactional
    public List<Post> getAllPosts(Optional<Long> userId) {
        List<Post> list;
        if (userId.isPresent())
            list = postDao.findByUserId(userId.get().longValue());
        else
            list= postDao.findAll();
        return list;
    }

    public List<PostResponse> convertPostListToPostResponseList(List<Post> posts) {
        return posts.stream().map(p -> {
            int followerCount = p.getUser().getFollowerCount();
            int followingCount = p.getUser().getFollowingCount();
            return new PostResponse(p, followerCount, followingCount);
        }).collect(Collectors.toList());
    }

    public Post getPostById(Long postId) {
        return postDao.findById(postId).orElse(null);
    }

    public Post getPostByIdWithLikes(Long postId) {
        return postDao.findById(postId).orElse(null);
    }

    public Post savePost(PostCreateRequest postCreateRequest) {
        User user = userService.getUser(postCreateRequest.getUserId());
        if (user == null)
            return null;
        Post post = new Post();
        post.setText(postCreateRequest.getText());
        post.setTitle(postCreateRequest.getTitle());
        post.setUser(user);
        post.setCreateDate(new Date());
        return postDao.save(post);
    }

    public Post updatePostById(Long postId, PostUpdateRequest postUpdateRequest) {
        Optional<Post> post = postDao.findById(postId);
        if (post.isPresent()){
            Post postTemp = post.get();
            postTemp.setText(postUpdateRequest.getText());
            postTemp.setTitle(postUpdateRequest.getText());
            return postDao.save(postTemp);
        }
        return null;
    }

    public void deletePostById(Long postId) {
        postDao.deleteById(postId);
    }

    public void addLike(Post post) {
        try {
            if (post.getCountLike() < 0)
                throw new Exception("Like sayısı 0 dan az olamaz");
            post.setCountLike(post.getCountLike()+1);
            postDao.save(post);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLike(Post post) {
        try {
            if (post.getCountLike()<1)
                throw new Exception("Like sayısı 0 dan az olamaz");
            post.setCountLike(post.getCountLike()-1);
            postDao.save(post);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
