package de.softwaretechnik.models;

import de.softwaretechnik.program.Program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class storing a connection to a database;
 * the connection will be tried to open using the root user and an empty password
 *
 * @author Malte Kasolowsky
 */
final class DBModel {
    private static final DBModel INSTANCE;
    private static final SQLException INSTANTIATION_EXCEPTION;

    static {
        DBModel inst = null;
        SQLException instantiationException = null;
        try {
            inst = new DBModel();
        } catch (SQLException e) {
            instantiationException = e;
        }
        INSTANCE = inst;
        INSTANTIATION_EXCEPTION = instantiationException;
    }

    private final Connection connection;

    /**
     * Private constructor, as only one static instantiation for the Singleton will be performed
     *
     * @throws SQLException If {@link DriverManager#getConnection(String, String, String)} fails
     */
    private DBModel() throws SQLException {
        connection = DriverManager.getConnection(Program.DB_URL, "root", null);
    }

    /**
     * Singleton getter
     *
     * @return The DBModel Singleton
     */
    static DBModel getInstance() {
        if (INSTANTIATION_EXCEPTION != null) {
            throw new IllegalStateException(
                    "a failure occurred previously when creating the DBModel instance",
                    INSTANTIATION_EXCEPTION
            );
        }
        return INSTANCE;
    }

    /**
     * Getter for the {@link Connection} to the database
     *
     * @return The connection
     */
    Connection getConnection() {
        return connection;
    }
}
