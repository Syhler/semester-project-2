package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthenticationDialogController
{
    @FXML
    private Label dialogText;

    public Label getDialogText() {
        return dialogText;
    }

    public void openDialog(ActionEvent event, String text) throws IOException {
        var dialog = new Stage();

        var loader = App.getLoader("authenticationPopup");

        dialog.setTitle("Login status");
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(((Node)event.getTarget()).getScene().getWindow());
        dialog.setResizable(false);
        dialog.setScene(new Scene(loader.load()));

        var controller = loader.<AuthenticationDialogController>getController();
        controller.getDialogText().setText(text);

        dialog.show();
    }

    public void dialogOkayAction(ActionEvent event)
    {
        closeDialog(event);
    }

    private void closeDialog(ActionEvent event) {
        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }


}
