package com.company.ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 *Created by Berkay KADAMLI
 */
public class ConnectionUtil {
    public static Connection conDB() {
        Connection connection=null;
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn =  DriverManager.getConnection("jdbc:postgresql://localhost:5432/deneme", "postgres", "password");
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
