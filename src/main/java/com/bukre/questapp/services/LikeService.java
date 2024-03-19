package com.bukre.questapp.services;

import com.bukre.questapp.entities.Like;
import com.bukre.questapp.entities.Post;
import com.bukre.questapp.entities.User;
import com.bukre.questapp.repos.LikeRepository;
import com.bukre.questapp.requests.LikeCreateRequest;
import com.bukre.questapp.responses.LikeResponse;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class LikeService {
    private LikeRepository likeRepository;
    private UserService userService;
    private PostService postService;

    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository=likeRepository;
        this.postService=postService;
        this.userService=userService;
    }

    public List<LikeResponse> getAllLikesWithParams(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if(userId.isPresent() && postId.isPresent()){
            list = likeRepository.findAllByUserIdAndPostId(userId.get(),postId.get());
        } else if (userId.isPresent()) {
            list = likeRepository.findByUserId(userId.get());
        }else if (postId.isPresent()){
            list = likeRepository.findByPostId(postId.get());
        }
        else
            list = likeRepository.findAll();
       return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }
    public Like getOneLikeById(Long commentId) {

        return likeRepository.findById(commentId).orElse(null);
    }

    public Like createOneLike(LikeCreateRequest request) {
        User user = userService.getOneUserById(request.getUserId());
        Post post = postService.getOnePostById(request.getPostId());
        if (user != null && post != null) {
            Like likeToSave = new Like();
            likeToSave.setId(request.getId());
            likeToSave.setPost(post);
            likeToSave.setUser(user);

            return likeRepository.save(likeToSave);
        }
        else
            return null;
    }


    public void deleteOneLikeById(Long likeId) {

        likeRepository.deleteById(likeId);
    }
}
