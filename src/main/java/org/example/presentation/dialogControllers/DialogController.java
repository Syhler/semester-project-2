package org.example.presentation.dialogControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.presentation.utilities.ControllerUtility;

import java.io.IOException;

public class DialogController
{

    @FXML
    private Label statusLabel;


    /**
     * Open a importDialog
     */
    public void openDialog(ActionEvent event, String text, String title)
    {
        var dialogStage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("dialog/dialog.fxml"));

            dialogStage.setScene(new Scene(loader.load()));
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(((Node)event.getTarget()).getScene().getWindow());
            dialogStage.setResizable(false);

            DialogController controller = loader.getController();
            controller.statusLabel.setText(text);
            dialogStage.getIcons().add(new Image(App.class.getResourceAsStream("loginImages/tv2trans.png")));

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void okOnAction(ActionEvent event) {
        ControllerUtility.closeProgram(event);
    }
}
