package tr.anil.questapp.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tr.anil.questapp.dao.CommentDao;
import tr.anil.questapp.entity.Comment;
import tr.anil.questapp.entity.Post;
import tr.anil.questapp.entity.User;
import tr.anil.questapp.request.CommentCreateRequest;
import tr.anil.questapp.request.CommentUpdateRequest;
import tr.anil.questapp.response.CommentResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private CommentDao commentDao;
    private PostService postService;
    private UserService userService;

    public CommentService(CommentDao commentDao, PostService postService, UserService userService) {
        this.commentDao = commentDao;
        this.postService = postService;
        this.userService = userService;
    }


    @Transactional
    public List<CommentResponse> getAllComments(Optional<Long> userId, Optional<Long> postId) {
        List<Comment> list = new ArrayList<>();
        if (userId.isPresent() && postId.isPresent()) {
            list = commentDao.findByUserIdAndPostId(userId.get(),postId.get());
        }
        else if (userId.isPresent()) {
            list = commentDao.findAllByUserId(userId.get());
        }
        else if (postId.isPresent()) {
            list = commentDao.findAllByPostId(postId.get());
        }
        else {
            list = commentDao.findAll();
        }
        return list.stream().map(p -> new CommentResponse(p)).collect(Collectors.toList());
    }

    public CommentResponse getCommentById(Long commentId) {
        return new CommentResponse(commentDao.findById(commentId).orElse(null));
    }

    public CommentResponse save(CommentCreateRequest commentCreateRequest) {
        User user = userService.getUser(commentCreateRequest.getUserId());
        if (user == null)
            return null;
        Post post = postService.getPostById(commentCreateRequest.getPostId());
        if (user == null)
            return null;

        Comment comment = new Comment();
        comment.setText(commentCreateRequest.getText());
        comment.setUser(user);
        comment.setPost(post);
        return new CommentResponse(commentDao.save(comment));
    }


    public CommentResponse updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> comment = commentDao.findById(commentId);
        if (comment.isPresent()){
            Comment newComment = comment.get();
            newComment.setText(commentUpdateRequest.getText());
            return new CommentResponse(commentDao.save(newComment));
        }
        return null;
    }

    public void deleteCommentById(Long commentId) {
        commentDao.deleteById(commentId);
    }
}
