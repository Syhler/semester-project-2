package org.example.presentation.dialogControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;

import java.io.IOException;

public class ImportDialogController
{

    @FXML
    private Label statusLabel;

    public void setStatusLabelText(String statusText) {
        this.statusLabel.setText(statusText);
    }

    /**
     * Open a importDialog
     * @param text the text you want to display on the popup dialog.
     */
    public void openDialog(ActionEvent event, String text)
    {

        var dialogStage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("dialog/importDialog.fxml"));

            dialogStage.setScene(new Scene(loader.load()));
            dialogStage.setTitle("Import Dialog");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(((Node)event.getTarget()).getScene().getWindow());
            dialogStage.setResizable(false);

            ImportDialogController controller = loader.getController();
            controller.setStatusLabelText(text);

            dialogStage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * what happen when clicking the ok button
     */
    @FXML
    public void okOnAction(ActionEvent event)
    {
        //gets the node of the given event
        Node source = (Node)  event.getSource();
        //gets that nodes stage
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
