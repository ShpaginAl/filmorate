package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    UserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(int idOfUser, int idOfFriend) {
        userStorage.findUserById(idOfUser).getFriends().add(idOfFriend);
        log.info("Добавлен друг у пользователя c айди: {}", idOfUser);
        userStorage.findUserById(idOfFriend).getFriends().add(idOfUser);
        log.info("Добавлен друг у пользователя c айди: {}", idOfFriend);
    }

    public void deleteFriend(int idOfUser, int idOfFriend) {
        userStorage.findUserById(idOfUser).getFriends().remove(idOfFriend);
        log.info("У пользователя c id: {} удален друг с id: {}", idOfUser, idOfFriend);
        userStorage.findUserById(idOfFriend).getFriends().remove(idOfUser);
        log.info("У пользователя c id: {} удален друг с id: {}", idOfFriend, idOfUser);
    }

    public Set<User> showListOfFriends(int idOfUser) {
        User user = userStorage.findUserById(idOfUser);
        return user.getFriends().stream()
                .map(userStorage::findUserById)
                .collect(Collectors.toSet());
    }

    public Set<User> showListOfCommonFriends(int userId, int otherId) {
        User user = userStorage.findUserById(userId);
        User otherUser = userStorage.findUserById(otherId);
        Set<Integer> friendsOfUser = user.getFriends();
        Set<Integer> friendOfOtherUser = otherUser.getFriends();
        return friendsOfUser.stream()
                .filter(friendOfOtherUser::contains)
                .map(userStorage::findUserById)
                .collect(Collectors.toSet());
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User newUser) {
        return userStorage.updateUser(newUser);
    }

    public User findUserById(int id) {
        return userStorage.findUserById(id);
    }


}
