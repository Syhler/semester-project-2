module com.semesterproject.two.semester.project {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.semesterproject.two.semester.project to javafx.fxml;
    exports com.semesterproject.two.semester.project;
}