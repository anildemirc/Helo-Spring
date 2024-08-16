package tr.anil.questapp.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tr.anil.questapp.dao.CommentDao;
import tr.anil.questapp.entity.Comment;
import tr.anil.questapp.entity.Post;
import tr.anil.questapp.entity.User;
import tr.anil.questapp.enums.NotificationTypes;
import tr.anil.questapp.exception.PostNotFoundException;
import tr.anil.questapp.exception.UserNotFoundException;
import tr.anil.questapp.request.CommentCreateRequest;
import tr.anil.questapp.request.CommentUpdateRequest;
import tr.anil.questapp.response.CommentResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private CommentDao commentDao;
    private PostService postService;
    private UserService userService;
    private NotificationService notificationService;

    public CommentService(CommentDao commentDao, PostService postService, UserService userService, NotificationService notificationService) {
        this.commentDao = commentDao;
        this.postService = postService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @Transactional
    public List<Comment> getAllComments(Optional<Long> userId, Optional<Long> postId) {
        List<Comment> list;
        if (userId.isPresent() && postId.isPresent()) {
            if (userService.getUser(userId.get().longValue()) == null)
                throw new UserNotFoundException();
            if (postService.getPostById(postId.get().longValue()) == null)
                throw new PostNotFoundException();
            list = commentDao.findByUserIdAndPostId(userId.get(),postId.get());
        }
        else if (userId.isPresent()) {
            if (userService.getUser(userId.get().longValue()) == null)
                throw new UserNotFoundException();
            list = commentDao.findAllByUserId(userId.get());
        }
        else if (postId.isPresent()) {
            if (postService.getPostById(postId.get().longValue()) == null)
                throw new PostNotFoundException();
            list = commentDao.findAllByPostId(postId.get());
        }
        else {
            list = commentDao.findAll();
        }
        return list;
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
        comment.setCreateDate(new Date());
        comment = commentDao.save(comment);
        notificationService.saveNotification(commentCreateRequest.getUserId(), post.getUser().getId(), post.getId(), comment.getId(), NotificationTypes.COMMENT.getNotificationTypes());
        return comment;
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
