package org.example.persistence;

import java.sql.*;
import org.postgresql.Driver;

public abstract class BasePersistence
{

    protected Connection initializeDatabase()
    {
        try {
            DriverManager.registerDriver(new Driver());
            String username = "postgres";
            int port = 5432;
            String host = "46.101.108.98";
            String password = "test";
            String databaseName = "test";
            return DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + databaseName, username, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
