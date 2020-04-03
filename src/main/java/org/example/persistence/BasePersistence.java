package org.example.persistence;

import java.sql.*;

public abstract class BasePersistence
{

    protected PersistenceHandler persistenceHandler = new PersistenceHandler();

    protected Connection initializeDatabase()
    {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            String username = "postgres";
            int port = 5432;
            String host = "46.101.108.98";
            String password = "test";
            String databaseName = "tv2system";
            return DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + databaseName, username, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
