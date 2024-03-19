package com.bukre.questapp.requests;

import lombok.Data;

@Data
public class LikeCreateRequest {
    Long Id;
    Long userId;
    Long postId;

}
