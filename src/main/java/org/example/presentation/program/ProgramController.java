package org.example.presentation.program;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.domain.DomainHandler;
import org.example.entity.ProgramEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgramController implements Initializable {
    public VBox program;
    public VBox programThumbnail;
    public Label title;
    public ImageView programImage;
    public ProgramEntity programEntity;
    private DomainHandler domainHandler = new DomainHandler();


    /**
     * On the click of a button, opens scene of the programEntity that user have clicked
     */
    @FXML
    public void goToProgramInfo()
    {
        ProgramInformationController programInformationController = new ProgramInformationController();
        programInformationController.openView(programEntity);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
