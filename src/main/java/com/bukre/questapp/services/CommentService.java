package com.bukre.questapp.services;

import com.bukre.questapp.entities.Comment;
import com.bukre.questapp.entities.Post;
import com.bukre.questapp.entities.User;
import com.bukre.questapp.repos.CommentRepository;
import com.bukre.questapp.requests.CommentCreateRequest;
import com.bukre.questapp.requests.CommentUpdateRequest;
import com.bukre.questapp.responses.CommentResponse;
import com.bukre.questapp.responses.PostResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;
    public CommentService(CommentRepository commentRepository,UserService userService,PostService postService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService= userService;
    }

    @Transactional
    public List<CommentResponse> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
        List<Comment> comments;
        if(userId.isPresent() && postId.isPresent()) {
            comments = commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }else if(userId.isPresent()) {
            comments = commentRepository.findByUserId(userId.get());
        }else if(postId.isPresent()) {
            comments = commentRepository.findByPostId(postId.get());
        }else
            comments = commentRepository.findAll();
        return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
    }

    public Comment getOneCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest request) {
        User user = userService.getOneUserById(request.getUserId());
        Post post = postService.getOnePostById(request.getPostId());
        if (user != null && post != null) {
            Comment commentToSave = new Comment();
            commentToSave.setId(request.getId());
            commentToSave.setPost(post);
            commentToSave.setUser(user);
            commentToSave.setText(request.getText());
            commentToSave.setCreateDate(new Date());
            return commentRepository.save(commentToSave);
        }
        else
            return null;
    }

    public Comment updateOneCommentById(Long commentId, CommentUpdateRequest request) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            Comment commentToUpdate = comment.get();
            commentToUpdate.setText(request.getText());
            return commentRepository.save(commentToUpdate);
        }
        else
            return null;
    }

    public void deleteOneCommentById(Long commentId) {

        commentRepository.deleteById(commentId);
    }
}
