package org.example.presentation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.domain.DomainHandler;
import org.example.entity.ProgramEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgramController implements Initializable {
    public VBox program;
    public VBox programThumbnail;
    public Label title;
    public ImageView programImage;
    private DomainHandler domainHandler;
    public ProgramEntity programEntity;


    @FXML
    private void goToCredit() throws IOException {
    }

    @FXML
    private void createProgram() throws IOException {
    }

    @FXML
    private void deleteProgram() throws IOException {
    }

    @FXML
    private void updateProgram() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
