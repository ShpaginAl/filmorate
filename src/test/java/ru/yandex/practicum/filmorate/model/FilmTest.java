package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.annotations.ValidationGroups;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FilmTest {
    Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void checkNotBlankNameAndDescription() {
        Film film = new Film(1, " ", " ", LocalDate.of(2010, 11, 25), 100);
        Set<ConstraintViolation<Film>> violations = validator.validate(film, Default.class, ValidationGroups.UpdateGroup.class, ValidationGroups.CreateGroup.class);
        assertThat(violations).hasSize(2);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("Название не может быть пустым");
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("описание не может быть пустым");
    }

    @Test
    void sizeOfDescriptionLessThen200() {
        String description204 = "а".repeat(204);
        String description200 = "а".repeat(200);
        String description190 = "а".repeat(190);
        Film film = new Film(1, "Боб", description204, LocalDate.of(2010, 11, 25), 100);
        Film film2 = new Film(1, "Боб", description200, LocalDate.of(2010, 11, 25), 100);
        Film film3 = new Film(1, "Боб", description190, LocalDate.of(2010, 11, 25), 100);
        List<ConstraintViolation<Film>> violations = new ArrayList<>();
        violations.addAll(validator.validate(film, ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class));
        violations.addAll(validator.validate(film2, ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class));
        violations.addAll(validator.validate(film3, ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class));
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("Максимальная длина описания - 200 символов");
    }

    @Test
    void releaseDateNotNull() {
        Film film = new Film(1, "аца", "пцупцу", 100);
        Set<ConstraintViolation<Film>> violations = validator.validate(film, Default.class, ValidationGroups.UpdateGroup.class, ValidationGroups.CreateGroup.class);
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("Дата релиза должна быть");
    }

    @Test
    void durationIsPositive() {
        Film film = new Film(1, "аца", "пцупцу", LocalDate.of(2010, 11, 25), -100);
        Set<ConstraintViolation<Film>> violations = validator.validate(film, Default.class, ValidationGroups.UpdateGroup.class, ValidationGroups.CreateGroup.class);
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("продолжительность фильма должна быть положительным числом");
    }
}
