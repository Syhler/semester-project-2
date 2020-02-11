module com.semesterproject.two.semester.project.two {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.semesterproject.two.semester.project.two to javafx.fxml;
    exports com.semesterproject.two.semester.project.two;
}