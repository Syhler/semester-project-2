package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
    public ProgramEntity programEntity;
    private DomainHandler domainHandler = new DomainHandler();


    @FXML
    private void goToCredit() throws IOException {
    }

    @FXML
    private void createProgram() throws IOException {
    }



    /**
     * On the click of a button, opens scene of the programEntity that user have clicked
     */
    @FXML
    public void goToProgramInfo()
    {
        ProgramInformationController programInformationController = new ProgramInformationController();
        programInformationController.openView(programEntity);
    }


    @FXML
    private void updateProgram() throws IOException {

    }

    /*//Bruges ikke lige nu.
    @FXML
    private void openDelete(ActionEvent event) throws  IOException {
        DeleteController deleteController = new DeleteController();
        deleteController.openView();
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
