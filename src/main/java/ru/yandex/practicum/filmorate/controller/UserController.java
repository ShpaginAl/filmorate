package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.annotations.ValidationGroups;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;


@Validated
@RestController
public class UserController {
    private HashMap<Integer, User> listOfUsers = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Collection<User> getAllUsers() {
        return listOfUsers.values();
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @Validated(ValidationGroups.CreateGroup.class)
    public User createUser(@Valid @RequestBody User user) {
        if (user.getName() == null) {
            log.info("Так как имя пользователя пустое, то имя = логину");
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        log.info("Пользователю присвоен айди {}", user.getId());
        listOfUsers.put(user.getId(), user);
        log.info("Пользователь {} добавлен в список", user);
        return user;
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    @Validated(ValidationGroups.UpdateGroup.class)
    public User updateUser(@Valid @RequestBody User newUser) {
        if (listOfUsers.containsKey(newUser.getId())) {
            User oldUser = listOfUsers.get(newUser.getId());
            oldUser.setName(newUser.getName());
            oldUser.setEmail(newUser.getEmail());
            oldUser.setBirthday(newUser.getBirthday());
            oldUser.setLogin(newUser.getLogin());
            log.info("Обновлен пользователь. Было {}, стало {}", oldUser, newUser);
            return newUser;
        }
        log.error("В списке нет автора с id {}", newUser.getId());
        throw new NotFoundException("Автора с айди" + newUser.getId() + "нет в списке");
    }

    private int getNextId() {
        int currentMaxId = listOfUsers.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
