package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.domain.DomainHandler;
import org.example.entity.UserEntity;

import java.io.IOException;

public class AuthenticationController {

    @FXML
    public Button btnLogin;

    @FXML
    public PasswordField passwordField;

    @FXML
    public TextField usernameField;

    private DomainHandler domainHandler = new DomainHandler();


    public UserEntity openLoginStage(ActionEvent event)
    {
        var loginStage = new Stage();

        try {
            loginStage.setScene(new Scene(App.getLoader("authentication").load()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        loginStage.setTitle("Login status");
        loginStage.initModality(Modality.WINDOW_MODAL);
        loginStage.initOwner(((Node)event.getTarget()).getScene().getWindow());
        loginStage.setResizable(false);
        loginStage.showAndWait();
        return CurrentUser.getInstance().getUserEntity();
    }


    @FXML
    public void login(ActionEvent actionEvent) throws IOException
    {
        var dialogController = new AuthenticationDialogController();

        if (usernameField.getText().isEmpty())
        {
            dialogController.openDialog(actionEvent, "Username is empty");
            return;
        }
        else if (passwordField.getText().isEmpty())
        {
            dialogController.openDialog(actionEvent, "Password is empty");
            return;
        }

        var user = domainHandler.authentication().login(usernameField.getText(), passwordField.getText());

        if (user != null)
        {
            CurrentUser.getInstance(

            ).init(user);
            closeDialog(actionEvent);
        }
        else
        {
            dialogController.openDialog(actionEvent, "Login information was not correct... Please try agian");
        }
    }

    private void closeDialog(ActionEvent event) {
        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
