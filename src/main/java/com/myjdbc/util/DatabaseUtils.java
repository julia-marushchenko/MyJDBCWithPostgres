package com.myjdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *  Database connectivity.
 */
public class DatabaseUtils {

    // Constants of the project.
    private static final String URL = "jdbc:postgresql://localhost:5432/shop";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "toor";
    private static Connection connection;


    // Getting a connection to Postgres database.
    public static Connection getConnection() {
        synchronized (DatabaseUtils.class) {
            if(connection == null) {
                try {
                    connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return connection;
    }
}
