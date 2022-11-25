package de.softwaretechnik.models;

import de.softwaretechnik.program.Program;

import java.sql.*;

/*

    DB Modell als Singelton
 */

public class DBModel {

    private static DBModel instance = new DBModel();
    private static Connection connection;
    private static Statement statement;

    private DBModel(){
        try {
            connection = DriverManager.getConnection(Program.DBCON, "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBModel getInstance(){
        return instance;
    }


}
