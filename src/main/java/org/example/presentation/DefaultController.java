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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.domain.DomainHandler;
import org.example.entity.ProgramEntity;
import org.example.presentation.multipleLanguages.LanguageHandler;

public class DefaultController implements Initializable
{

    public Button login;
    public Button createProgram;
    @FXML
    public BorderPane borderPane;
    public Label loggedInAs;
    public Label searchHeader;
    public TextField searchField;
    private DomainHandler domainHandler = new DomainHandler();
    public ProgramListController programListController;

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

    /**
     * On the click of a button, opens the scene to create a program
     * @param event
     * @throws IOException
     */
    @FXML
    private void goToCreateProgram(ActionEvent event) throws IOException {

        CreateProgramController createProgramController = new CreateProgramController();
        ProgramEntity programEntity = createProgramController.openView();
        if (programEntity != null)
        {
            programListController.programEntityList.add(programEntity);
            programListController.updateProgramList();
        }
    }

    /**
     * Loads the "programList.fxml" scene in the center of the borderpane
     */
    public void loadProgramList()
    {
        FXMLLoader loader = null;
        try {
            loader = App.getLoader("programList");
            Parent node = loader.load();
            programListController = loader.getController();
            programListController.programEntityList.addAll(domainHandler.program().getAllPrograms());
            programListController.updateProgramList();
            borderPane.setCenter(node);
        } catch (IOException e) {
            e.printStackTrace();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createProgram.setText(LanguageHandler.getText("createProgram"));
        loggedInAs.setText(LanguageHandler.getText("loggedInAs"));
        loadProgramList();

    }
}
