package org.example.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
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
    private void goToCreateProgram(ActionEvent event) throws IOException {
        Parent root = App.getLoader("createProgram").load();

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle(LanguageHandler.getText("createProgramStageTitle"));
        stage.setScene(scene);
        stage.show();
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
