package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.annotations.ValidationGroups;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Validated
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private HashMap<Integer, Film> listOfFilms = new HashMap<>();

    @GetMapping
    public Collection<Film> getAllFilms() {
        return listOfFilms.values();
    }

    @PostMapping
    @Validated(ValidationGroups.CreateGroup.class)
    public Film createFilm(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Передана некорректная дата релиза {}!", film.getReleaseDate());
            throw new ValidationException("дата релиза — не должна быть раньше 28 декабря 1895 года");
        }
        film.setId(generateId());
        log.info("Для фильма добавлен id {}", film.getId());
        listOfFilms.put(film.getId(), film);
        log.info("В список добавлен фильм {}", film);
        return film;
    }

    @PutMapping
    @Validated(ValidationGroups.UpdateGroup.class)
    public Film updateFilm(@Valid @RequestBody Film newFilm) {
        if (listOfFilms.containsKey(newFilm.getId())) {
            if (newFilm.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                log.error("Передана некорректная дата релиза для обновления {}!", newFilm.getReleaseDate());
                throw new ValidationException("дата релиза — не должна быть раньше 28 декабря 1895 года");
            }
            Film oldFilm = listOfFilms.get(newFilm.getId());
            oldFilm.setName(newFilm.getName());
            log.info("Обновлено название фильма c {} на {}", oldFilm.getName(), newFilm.getName());
            oldFilm.setDescription(newFilm.getDescription());
            log.info("Обновлено описание фильма c {} на {}", oldFilm.getDescription(), newFilm.getDescription());
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            log.info("Обновлена дата релиза фильма c {} на {}", oldFilm.getReleaseDate(), newFilm.getReleaseDate());
            oldFilm.setDuration(newFilm.getDuration());
            log.info("Обновлена продолжительность фильма c {} на {}", oldFilm.getDuration(), newFilm.getDuration());
            return newFilm;
        }
        log.error("В списке нет фильма с заданным id");
        throw new NotFoundException("Фильма с айди" + newFilm.getId() + "нет в списке");
    }

    public int generateId() {
        int currentMaxId = listOfFilms.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}

