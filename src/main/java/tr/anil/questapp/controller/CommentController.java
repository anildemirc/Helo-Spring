package tr.anil.questapp.controller;

import org.springframework.web.bind.annotation.*;
import tr.anil.questapp.entity.Comment;
import tr.anil.questapp.request.CommentCreateRequest;
import tr.anil.questapp.request.CommentUpdateRequest;
import tr.anil.questapp.response.CommentResponse;
import tr.anil.questapp.service.CommentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentResponse> getAllComments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId) {
        return commentService.getAllComments(userId,postId);
    }

    @GetMapping("/{commentId}")
    public CommentResponse getCommentById(@PathVariable Long commentId) {
        return commentService.getCommentById(commentId);
    }

    @PostMapping
    public CommentResponse saveComment(@RequestBody CommentCreateRequest commentCreateRequest) {
        return commentService.save(commentCreateRequest);
    }

    @PutMapping("/{commentId}")
    public CommentResponse updateComment(@PathVariable Long commentId,@RequestBody CommentUpdateRequest commentUpdateRequest) {
        return commentService.updateComment(commentId, commentUpdateRequest);
    }

    @DeleteMapping("/{commentId}")
    public void deleteCommentById(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);
    }


}
