package xyz.dmfe.ignite.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.dmfe.ignite.dtos.ApiError;

import java.util.UUID;

@RestControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException ex) {
        return ApiError.builder()
                .id(UUID.randomUUID().toString())
                .message("Not Found Error.")
                .description(ex.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleThrowable(Throwable ex) {
        return ApiError.builder()
                .id(UUID.randomUUID().toString())
                .message("Internal Server Error.")
                .description(ex.getLocalizedMessage())
                .build();
    }
}
