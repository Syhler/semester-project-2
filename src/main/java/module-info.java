module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires postgresql;
    //requires jdk.xml.dom;

    //opens org.example.presentation.multipleLanguages to jdk.xml.dom;
    opens org.example to javafx.fxml;
    opens org.example.presentation to javafx.fxml;

    exports org.example;
    exports org.example.presentation;

    //exports org.example.presentation.multipleLanguages;

}