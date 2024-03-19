package com.bukre.questapp.responses;

import com.bukre.questapp.entities.User;
import lombok.Data;

@Data
public class UserResponse {
    Long id;
    String userName;
    int avatar;

    public UserResponse(User entity) {
        this.id = entity.getId();
        this.userName = entity.getUserName();
        this.avatar =entity.getAvatar();
    }
}
