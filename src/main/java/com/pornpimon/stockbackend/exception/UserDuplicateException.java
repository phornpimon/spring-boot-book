package com.pornpimon.stockbackend.exception;

public class UserDuplicateException extends RuntimeException{

    public UserDuplicateException(String username) {
        super("Username: "+ username + " already exits");
    }
    
}
