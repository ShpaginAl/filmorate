package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    @Getter
    HashMap<Integer, User> listOfUsers = new HashMap<>();

    @Override
    public Collection<User> getAllUsers() {
        return listOfUsers.values();
    }

    @Override
    public User createUser(User user) {
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

    @Override
    public User updateUser(User newUser) {
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

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public User findUserById(int id) {
        if (listOfUsers.containsKey(id)) {
            return listOfUsers.get(id);
        }
        throw new NotFoundException("Пользователь с таким айди не найден");
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
