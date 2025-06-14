package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    public Film createFilm(Film film);

    public Collection<Film> getAllFilms();

    public Film updateFilm(Film newFilm);

    public Film findFilmById(int id);

    public void deleteFilm(int id);

}
