package com.bukre.questapp.controllers;

import com.bukre.questapp.entities.User;
import com.bukre.questapp.exceptions.UserNotFoundException;
import com.bukre.questapp.responses.UserResponse;
import com.bukre.questapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    //Constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //tüm userları getiren controller
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // yebi bir user create etmek için =newUser
    @PostMapping
    public User createUser(@RequestBody User newUser) {
        return userService.saveOneUser(newUser);
    }

    @GetMapping("/{userId}")
    public UserResponse getOneUser(@PathVariable Long userId) {
        //eğer user yoksa exception fırlat TODO : usernotfoundexception
        User user = userService.getOneUserById(userId);
        if (user == null){
            throw new UserNotFoundException();
        }
        return  new UserResponse(user);
    }

    @PutMapping("/{userId}")
    public User updateOneUser(@PathVariable Long userId, @RequestBody User newUser) {
        return userService.updateOneUser(userId, newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId) {
        userService.deleteById(userId);
    }

    @GetMapping("/activity/{userId}")
    public List<Object> getUserActivity(@PathVariable Long userId){
    return userService.getUserActivity(userId);
    }
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleUserNotFound() {

    }
}


