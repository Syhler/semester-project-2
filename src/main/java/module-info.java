module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires postgresql;


    opens org.example to javafx.fxml;
    opens org.example.presentation to javafx.fxml;
    opens org.example.entity to javafx.base;
    opens org.example.presentation.program to javafx.fxml;

    exports org.example;
    exports org.example.entity;
    exports org.example.presentation;
    exports org.example.presentation.program;
}
