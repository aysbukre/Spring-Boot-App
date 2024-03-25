package com.bukre.questapp.requests;

import lombok.Data;

//TODO niye böyle bir dosya var?
@Data
public class CommentCreateRequest {
    Long Id;
    Long userId;
    Long postId;
    String text;
}
