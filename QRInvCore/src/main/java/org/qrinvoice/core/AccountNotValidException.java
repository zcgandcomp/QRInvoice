package org.qrinvoice.core;

/**
 * Created by zcg on 11.6.2016.
 */
public class AccountNotValidException extends Exception {
    public AccountNotValidException() {
    }

    public AccountNotValidException(Throwable cause) {
        super(cause);
    }

    public AccountNotValidException(String message) {
        super(message);
    }

    public AccountNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
