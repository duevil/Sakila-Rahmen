package de.softwaretechnik.models;

import de.softwaretechnik.program.Program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    private DBModel() throws SQLException {
        connection = DriverManager.getConnection(Program.DB_URL, "root", null);
    }

    static DBModel getInstance() {
        if (INSTANTIATION_EXCEPTION != null) {
            throw new IllegalStateException(
                    "a failure occurred previously when creating the DBModel instance",
                    INSTANTIATION_EXCEPTION
            );
        }
        return INSTANCE;
    }

    Connection getConnection() {
        return connection;
    }
}
