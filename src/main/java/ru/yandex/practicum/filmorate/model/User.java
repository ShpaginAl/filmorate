package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotations.ValidationGroups;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class User {

    @NotNull(message = "При обновлении ресурса id не может быть нулевым", groups = ValidationGroups.UpdateGroup.class)
    private int id;

    @NotBlank(message = "email не может быть пустым")
    @NotNull(message = "email не может быть null")
    @Email(message = "Почта должна соответствовать правилам почты")
    private String email;

    @NotNull(message = "Логин не может быть незаполненным")
    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(message = "Логин не может иметь пробелы", regexp = "^\\S+$")
    private String login;

    private String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;

    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public User(int id, String email, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
