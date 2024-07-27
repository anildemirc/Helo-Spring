package tr.anil.questapp.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.anil.questapp.dao.PostDao;
import tr.anil.questapp.entity.Like;
import tr.anil.questapp.entity.Post;
import tr.anil.questapp.entity.User;
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

    public PostService(PostDao postDao, UserService userService,LikeService likeService) {
        this.postDao = postDao;
        this.userService = userService;
        this.likeService = likeService;
    }

    @Transactional
    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> list;
        if (userId.isPresent())
            list= postDao.findByUserId(userId.get().longValue());
        else
            list= postDao.findAll();
        return list.stream().map(p -> {
            List<LikeResponse> likes = likeService.getAllLike(Optional.empty(), Optional.of(p.getId()));
            return new PostResponse(p,likes);
        }).collect(Collectors.toList());
    }

    public Post getPostById(Long postId) {
        return postDao.findById(postId).orElse(null);
    }

    public PostResponse getPostByIdWithLikes(Long postId) {
        Post post = postDao.findById(postId).orElse(null);
        List<LikeResponse> likes = likeService.getAllLike(Optional.empty(), Optional.of(postId));
        return new PostResponse(post, likes);
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
}
