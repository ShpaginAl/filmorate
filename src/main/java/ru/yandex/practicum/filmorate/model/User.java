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

    @NotBlank (message = "email не может быть пустым")
    @NotNull (message = "email не может быть null")
    private String email;

    @NotNull (message = "Логин не может быть не заполненным")
    @NotBlank (message = "Логин не может быть пустым")
    @Pattern(regexp = "^\\S+$")
    private String login;

    private String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;
}
