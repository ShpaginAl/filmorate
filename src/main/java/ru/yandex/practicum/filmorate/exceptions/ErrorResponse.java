package ru.yandex.practicum.filmorate.exceptions;

public class ErrorResponse {
    private final String error;
    private final int code;

    public ErrorResponse(int code, String error) {
        this.code = code;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public int getCode() {
        return code;
    }
}
