package com.github.codedrinker.exception;

/**
 * Created by codedrinker on 28/07/2017.
 */
public class LastfmException extends Exception {
    public LastfmException(String message) {
        super(message);
    }

    public LastfmException(String message, Throwable cause) {
        super(message, cause);
    }
}
