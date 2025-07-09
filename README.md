# Filmorate Project

![ER Diagram](docs/er.png)

Несколько запросов:
1) Выводит названия фильмов и количество лайков, отсортированные по популярности.
SELECT f.name, COUNT(l.user_id) AS likes_count
FROM Film f
LEFT JOIN like_list l ON f.id = l.film_id
GROUP BY f.id
ORDER BY likes_count DESC
LIMIT 5;

2) Показывает фильмы, которые лайкнули друзья пользователя 123.
SELECT DISTINCT f.name
FROM Film f
JOIN like_list l ON f.id = l.film_id
JOIN Friendship fr ON l.user_id = fr.friend_id
WHERE fr.user_id = 123 AND fr.is_friendship_confirmed = TRUE;

3) Показать фильмы выпущенные после 2010 года
SELECT name, release_date
FROM Film
WHERE release_date > '2010-01-01'
ORDER BY release_date DESC;

4) Показывает пользователя с неподтвержденными заявками

SELECT u.name, u.login
FROM User u
JOIN Friendship f ON u.id = f.friend_id
WHERE f.is_friendship_confirmed = FALSE;

5) Показывает подтвержденных друзей пользователя 123

SELECT u.name, u.login
FROM User u
JOIN Friendship f ON u.id = f.friend_id
WHERE f.user_id = 123 AND f.is_friendship_confirmed = TRUE;
