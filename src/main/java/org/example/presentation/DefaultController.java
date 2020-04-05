package org.example.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.example.domain.DomainHandler;
import org.example.presentation.multipleLanguages.LanguageHandler;

public class DefaultController implements Initializable
{

    public Button login;
    public Button createProgram;
    @FXML
    public BorderPane borderPane;
    public Label loggedInAs;
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
    private void createProgram(ActionEvent event)
    {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createProgram.setText(LanguageHandler.getText("createProgram"));
        ProgramListController programListController = new ProgramListController();
        borderPane.setCenter(programListController.openView());
        loggedInAs.setText(LanguageHandler.getText("loggedInAs"));

    }
}
