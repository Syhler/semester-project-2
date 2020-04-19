package org.example.persistence;

import java.sql.*;

public abstract class BasePersistence
{

    protected Connection initializeDatabase()
    {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            String asdasdasfasd = "postgres";
            int fuasdadasdojo = 5432;
            String asdasdasdasdasd = "46.101.108.98";
            String asdljasjldhasjd = "SDU123";
            String asdasdasdasd = "tv2system";
            return DriverManager.getConnection("jdbc:postgresql://" + asdasdasdasdasd + ":" + fuasdadasdojo + "/" + asdasdasdasd, asdasdasfasd, asdljasjldhasjd);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
