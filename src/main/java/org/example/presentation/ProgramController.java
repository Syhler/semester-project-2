package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.domain.DomainHandler;
import org.example.entity.ProgramEntity;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgramController implements Initializable {
    public VBox program;
    public VBox programThumbnail;
    public Label title;
    public ImageView programImage;
    public TextArea infoTitle;
    public TextArea infoCompany;
    public TextArea infoProducer;
    public TextArea infoDescription;
    public TextArea infoCredits;
    public VBox programInfo;
    public Button updateProgInfoBtn;
    public Button deleteProgBtn;
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
    public void goToProgramInfo()
    {
        ProgramInformationController programInformationController = new ProgramInformationController();
        programInformationController.openView(programEntity);
    }

    @FXML
    private void updateProgram() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}
