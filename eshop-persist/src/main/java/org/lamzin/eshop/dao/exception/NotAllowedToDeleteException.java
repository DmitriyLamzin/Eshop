package org.lamzin.eshop.dao.exception;

/**
 * Created by Dmitriy on 08.10.2016.
 */
public class NotAllowedToDeleteException extends RuntimeException {
    public NotAllowedToDeleteException() {
    }

    public NotAllowedToDeleteException(String message) {
        super(message);
    }
}
