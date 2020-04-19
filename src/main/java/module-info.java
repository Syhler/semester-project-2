module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires postgresql;


    opens org.example to javafx.fxml;
    opens org.example.domain to javafx.base;
    opens org.example.presentation to javafx.fxml;
    opens org.example.presentation.program to javafx.fxml;
    opens org.example.presentation.usermangement to javafx.fxml;
    opens org.example.presentation.dialogControllers to javafx.fxml;

    exports org.example;
    exports org.example.domain;
    exports org.example.presentation;
    exports org.example.presentation.program;
    exports org.example.presentation.usermangement;
    exports org.example.presentation.dialogControllers;
}
