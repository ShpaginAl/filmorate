package ru.yandex.practicum.filmorate.service;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    FilmStorage inMemoryFilmStorage;
    UserStorage inMemoryUserStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public void addLike(int filmId, int userId) {
        Film film = inMemoryFilmStorage.findFilmById(filmId); //ищу есть ли такой фильм
        User user = inMemoryUserStorage.findUserById(userId); //ищу есть ли такой пользователь
        film.getListOfLikes().add(userId);
        log.info("добавлен лайк на фильм: {} пользователем: {}", filmId, userId);
    }

    public void removeLike(int filmId, int userId) {
        Film film = inMemoryFilmStorage.findFilmById(filmId); //ищу есть ли такой фильм
        User user = inMemoryUserStorage.findUserById(userId); //ищу есть ли такой пользователь
        film.getListOfLikes().remove(userId);
        log.info("Удален лайк пользователя: {} с фильма: {}", userId, filmId);
    }

    public List<Film> printTheMostPopularFilms(int count) {
        return inMemoryFilmStorage.getAllFilms().stream()
                .sorted(Comparator.comparing((Film film) -> film.getListOfLikes().size()).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    public Collection<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    public Film createFilm(Film film) {
        return inMemoryFilmStorage.createFilm(film);
    }

    public Film updateFilm(Film newFilm) {
        return inMemoryFilmStorage.updateFilm(newFilm);
    }

    public Film findById(int id) {
        return inMemoryFilmStorage.findFilmById(id);
    }
}
