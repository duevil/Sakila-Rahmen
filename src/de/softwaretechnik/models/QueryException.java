package de.softwaretechnik.models;

import java.sql.SQLException;

public class QueryException extends RuntimeException {
    QueryException(SQLException cause) {
        super(cause);
    }
}
