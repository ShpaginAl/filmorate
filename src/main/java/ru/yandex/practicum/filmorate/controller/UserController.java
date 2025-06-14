package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.annotations.ValidationGroups;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;


import java.util.Collection;


@Validated
@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @Validated(ValidationGroups.CreateGroup.class)
    public User createUser(@RequestBody @Valid User user) {
        return userService.createUser(user);
    }

    @PutMapping
    @Validated(ValidationGroups.UpdateGroup.class)
    public User updateUser(@RequestBody @Valid User newUser) {
        return userService.updateUser(newUser);
    }

    @GetMapping("/{id}")
    public User findById(int id) {
        return userService.findUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addToFriend(@PathVariable("id") int userId, @PathVariable("friendId") int friendId) {
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFromFriends(@PathVariable("id") int userId, @PathVariable("friendId") int friendId) {
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> listOfFriends(@PathVariable("id") int userId) {
        return userService.showListOfFriends(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> listOfCommonFriends(@PathVariable("id") int userId, @PathVariable("otherId") int otherId) {
        return userService.showListOfCommonFriends(userId, otherId);
    }
}
