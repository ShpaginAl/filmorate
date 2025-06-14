package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    public Collection<User> getAllUsers();

    public User createUser(User user);

    public User updateUser(User newUser);

    public void deleteUser(int id);

    public User findUserById(int id);
}
