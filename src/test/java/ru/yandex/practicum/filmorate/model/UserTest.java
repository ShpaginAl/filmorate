package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.annotations.ValidationGroups;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void isEmailRight() {
        User user = new User(1, "ff32mailri", "gewjgnlw", "buba", LocalDate.of(2010, 11, 25));
        Set<ConstraintViolation<User>> violations = validator.validate(user, Default.class, ValidationGroups.UpdateGroup.class, ValidationGroups.CreateGroup.class);
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("Почта должна соответствовать правилам почты");
    }

    @Test
    void EmailCannotBeBlank() {
        User user = new User(1, " ", "gewjgnlw", "buba", LocalDate.of(2010, 11, 25));
        Set<ConstraintViolation<User>> violations = validator.validate(user, Default.class, ValidationGroups.UpdateGroup.class, ValidationGroups.CreateGroup.class);
        assertThat(violations).hasSize(2);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("email не может быть пустым");
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("Почта должна соответствовать правилам почты");
    }

    @Test
    void EmailCannotBeNull() {
        User user = new User(1, null, "gewjgnlw", "buba", LocalDate.of(2010, 11, 25));
        Set<ConstraintViolation<User>> violations = validator.validate(user, Default.class, ValidationGroups.UpdateGroup.class, ValidationGroups.CreateGroup.class);
        assertThat(violations).hasSize(2);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("email не может быть null");
    }

    @Test
    void loginCannotContainSpace() {
        User user = new User(1, "fwf@mail.ru", "ekfelw fewf", "buba", LocalDate.of(2010, 11, 25));
        Set<ConstraintViolation<User>> violations = validator.validate(user, Default.class, ValidationGroups.UpdateGroup.class, ValidationGroups.CreateGroup.class);
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("Логин не может иметь пробелы");
    }

    @Test
    void loginCannotBeNullAndBlank() {
        User user = new User(1, "fwf@mail.ru", "ekfelw fewf", "buba", LocalDate.of(2010, 11, 25));
        Set<ConstraintViolation<User>> violations = validator.validate(user, Default.class, ValidationGroups.UpdateGroup.class, ValidationGroups.CreateGroup.class);
        assertThat(violations).hasSize(2);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("Логин не может быть пустым");
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("Логин не может быть незаполненным");
    }

    @Test
    void birthdayIsNotInFuture() {
        User user = new User(1, "fwf@mail.ru", "fefwf", "buba", LocalDate.of(2026, 11, 25));
        Set<ConstraintViolation<User>> violations = validator.validate(user, Default.class, ValidationGroups.UpdateGroup.class, ValidationGroups.CreateGroup.class);
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("Дата рождения не может быть в будущем");
    }
}
