package tr.anil.questapp.controller;


import org.springframework.web.bind.annotation.*;
import tr.anil.questapp.entity.Post;
import tr.anil.questapp.request.PostCreateRequest;
import tr.anil.questapp.request.PostUpdateRequest;
import tr.anil.questapp.response.PostResponse;
import tr.anil.questapp.service.PostService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId) {
        return postService.getAllPosts(userId);
    }

    @GetMapping("/{postId}")
    public Post getPost(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }


    @PostMapping
    public Post savePost(@RequestBody PostCreateRequest postCreateRequest) {
        return postService.savePost(postCreateRequest);
    }

    @PutMapping("/{postId}")
    public Post updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        return postService.updatePostById(postId, postUpdateRequest);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePostById(postId);
    }




}
