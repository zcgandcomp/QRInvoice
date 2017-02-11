package org.qrinvoice.core;

/**
 * Created by zcg on 11.6.2016.
 * exception if account format is not valid czech bank account number
 */
public class AccountNotValidException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2185749286961373876L;

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
