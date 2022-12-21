package de.softwaretechnik.models;

import java.sql.SQLException;

/**
 * Exception for wrapping a {@link SQLException} into a {@link RuntimeException}
 *
 * @author Malte Kasolowsky
 */
public class QueryException extends RuntimeException {
    /**
     * Constructor; calls {@link RuntimeException#RuntimeException(Throwable)}
     *
     * @param cause The causing {@link SQLException}
     */
    QueryException(SQLException cause) {
        super(cause);
    }
}
