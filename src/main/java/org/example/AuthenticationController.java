package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.example.domain.DomainHandler;

import java.io.IOException;

public class AuthenticationController {


    public Button btnLogin;
    public Label dialogText;
    public PasswordField passwordField;
    public TextField usernameField;

    private DomainHandler domainHandler;


    @FXML
    private void logout() throws IOException {

    }

    @FXML
    public void login(ActionEvent actionEvent) throws IOException
    {
        if (usernameField.getText().isEmpty())
        {
            openDialog(actionEvent, "Username is empty");
        }
        else if (passwordField.getText().isEmpty())
        {
            openDialog(actionEvent, "Password is empty");
        }

        var user = domainHandler.authentication().login(usernameField.getText(), passwordField.getText());

        if (user != null)
        {
            openDialog(actionEvent, "Login succeed");
            CurrentUser.getInstance().init(user);
        }
        else
        {
            openDialog(actionEvent, "Login information was not correct... Please try agian");
        }

    }

    private void openDialog(ActionEvent event, String text) throws IOException {
        var dialog = new Stage();
        dialog.setScene(new Scene(App.loadFXML("authenticationPopup")));
        dialog.setTitle("Login status");
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(((Node)event.getTarget()).getScene().getWindow());
        dialog.setResizable(false);
        dialogText.setText(text);
        dialog.show();
    }

    public void dialogOkayAction(ActionEvent actionEvent)
    {
        closeStage(actionEvent);
    }

    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
