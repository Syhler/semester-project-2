module Javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens org.example to javafx.fxml;
    exports org.example;
}