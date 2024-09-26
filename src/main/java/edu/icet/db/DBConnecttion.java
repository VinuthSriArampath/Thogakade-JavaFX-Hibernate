package edu.icet.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnecttion {
    private static DBConnecttion instance;
    private Connection getConnection;

    public static DBConnecttion getGetInstance() {
        if (instance==null){
            return instance=new DBConnecttion();
        }
        return instance;
    }

    public Connection getConnection() {
        if (getConnection==null){
            try {
                return getConnection=DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade","root","123456");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
       return getConnection;
    }
}
