package org.example.presentation.program;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.example.domain.Program;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgramController implements Initializable
{
    @FXML
    public VBox program;
    @FXML
    private VBox programThumbnail;
    @FXML
    public Label title;
    @FXML
    private ImageView programImage;

    public Program programEntity;


    /**
     * On the click of a button, opens scene of the programEntity that user have clicked
     */
    @FXML
    public void goToProgramInfo(MouseEvent event)
    {
        ProgramInformationController programInformationController = new ProgramInformationController();
        programInformationController.openView(programEntity, event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
