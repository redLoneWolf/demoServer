package com.example.demoserver;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static Connection connection = null;

    public static Connection getConnection() {
        if(connection!=null){
            return null;
        }

        DataSource ds;
        Context initContext = null;


        try {
            initContext = new InitialContext();
            ds = (DataSource)initContext.lookup("java:comp/env/jdbc/testdb1");
            connection = ds.getConnection();
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }
}
