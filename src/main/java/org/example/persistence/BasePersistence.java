package org.example.persistence;

import java.sql.*;

public abstract class BasePersistence
{

    protected PersistenceHandler persistenceHandler = new PersistenceHandler();

    protected Connection initializeDatabase()
    {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            String gofuckyouself = "postgres";
            int asldjaslkdjaslkæd = 5432;
            String slfdjdklasfjpdsafs = "46.101.108.98";
            String blalbvlallbllala = "SDU123";
            String asdkasdakmsædaækls = "tv2system";
            return DriverManager.getConnection("jdbc:postgresql://" + slfdjdklasfjpdsafs + ":" + asldjaslkdjaslkæd + "/" + asdkasdakmsædaækls, gofuckyouself, blalbvlallbllala);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
