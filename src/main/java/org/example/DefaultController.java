package org.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import org.example.domain.DomainHandler;

public class DefaultController
{

    public Button login;
    private DomainHandler domainHandler = new DomainHandler();

    @FXML
    private void goToLogin(ActionEvent event)
    {
        AuthenticationController authenticationController = new AuthenticationController();
        var userEntity = authenticationController.openLoginStage(event);

        if (userEntity != null)
        {
            login.setText("Logout");
            login.setOnAction(this::logout);
        }
    }

    private void logout(ActionEvent event)
    {
        if (CurrentUser.getInstance().getUserEntity() != null)
        {
            CurrentUser.getInstance().init(null); //Logs off
            login.setText("Login");
            login.setOnAction(this::goToLogin);
        }
    }

    @FXML
    private void goToSearch() throws IOException {

    }
    @FXML
    private void goToProgram() throws IOException {

    }
    @FXML
    private void goToUsermanagement() throws IOException {

    }
}
