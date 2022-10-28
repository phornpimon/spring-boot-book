package com.pornpimon.stockbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class ExceptionAdvice {
    
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerBookNotFound(BookNotFoundException bookNotFoundException) {
        return bookNotFoundException.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handlerStorageException(StorageException storageException) {
        return storageException.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerMaxUploadSize(MaxUploadSizeExceededException maxUploadSizeExceededException) {
        return "Maximum upload size exceeded";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerValidation(ValidationException validationException) {
        return validationException.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerUserDuplicate(UserDuplicateException userDuplicateException) {
        return userDuplicateException.getMessage();
    }
}
