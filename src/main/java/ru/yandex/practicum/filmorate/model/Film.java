package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotations.ValidationGroups;

import java.time.LocalDate;

@Data
public class Film {

    @NotNull(message = "При обновлении ресурса id не может быть нулевым", groups = ValidationGroups.UpdateGroup.class)
    private int id;

    @NotBlank (message = "Название не может быть пустым", groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    private String name;

    @NotBlank (message = "описание не может быть пустым", groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    @Size(message = "Максимальная длина описания - 200 символов", max = 200, groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    private String description;

    @NotNull
    private LocalDate releaseDate;

    @Positive(message = "продолжительность фильма должна быть положительным числом", groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    private int duration;


}
