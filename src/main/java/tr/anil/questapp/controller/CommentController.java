package tr.anil.questapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.anil.questapp.entity.Comment;
import tr.anil.questapp.exception.PostNotFoundException;
import tr.anil.questapp.exception.UserNotFoundException;
import tr.anil.questapp.request.CommentCreateRequest;
import tr.anil.questapp.request.CommentUpdateRequest;
import tr.anil.questapp.response.CommentResponse;
import tr.anil.questapp.service.CommentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllComments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId) {
        try {
            return new ResponseEntity<>(commentService.getAllComments(userId, postId).stream().map(p -> new CommentResponse(p)).collect(Collectors.toList()), HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (PostNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            throw e;
        }
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable Long commentId) {
        try {
            return new ResponseEntity<>(new CommentResponse(commentService.getCommentById(commentId)), HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (PostNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<CommentResponse> saveComment(@RequestBody CommentCreateRequest commentCreateRequest) {
        try {
            return new ResponseEntity<>(new CommentResponse(commentService.save(commentCreateRequest)), HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (PostNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            throw e;
        }
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long commentId,@RequestBody CommentUpdateRequest commentUpdateRequest) {
        try {
            return new ResponseEntity<>(new CommentResponse(commentService.updateComment(commentId, commentUpdateRequest)), HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (PostNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            throw e;
        }
    }

    @DeleteMapping("/{commentId}")
    public void deleteCommentById(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);
    }


}
