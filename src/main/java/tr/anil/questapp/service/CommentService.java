package tr.anil.questapp.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tr.anil.questapp.dao.CommentDao;
import tr.anil.questapp.entity.Comment;
import tr.anil.questapp.entity.Post;
import tr.anil.questapp.entity.User;
import tr.anil.questapp.request.CommentCreateRequest;
import tr.anil.questapp.request.CommentUpdateRequest;

import java.util.List;
import java.util.Optional;

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
    public List<Comment> getAllComments(Optional<Long> userId, Optional<Long> postId) {
        if (userId.isPresent() && postId.isPresent()) {
            return commentDao.findByUserIdAndPostId(userId.get(),postId.get());
        }
        else if (userId.isPresent()) {
            return commentDao.findAllByUserId(userId.get());
        }
        else if (postId.isPresent()) {
            return commentDao.findAllByPostId(postId.get());
        }
        return commentDao.findAll();


    }

    public Comment getCommentById(Long commentId) {
        return commentDao.findById(commentId).orElse(null);
    }

    public Comment save(CommentCreateRequest commentCreateRequest) {
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
        return commentDao.save(comment);
    }


    public Comment updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> comment = commentDao.findById(commentId);
        if (comment.isPresent()){
            Comment newComment = comment.get();
            newComment.setText(commentUpdateRequest.getText());
            return commentDao.save(newComment);
        }
        return null;
    }

    public void deleteCommentById(Long commentId) {
        commentDao.deleteById(commentId);
    }
}
