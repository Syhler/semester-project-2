module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires postgresql;

    opens org.example to javafx.fxml;
    opens org.example.presentation to javafx.fxml;
    exports org.example;
    exports org.example.presentation;
}
