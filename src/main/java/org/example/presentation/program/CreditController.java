package org.example.presentation.program;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.App;
import org.example.entity.Role;
import org.example.entity.UserEntity;
import org.example.presentation.utilities.ControllerUtility;
import org.example.presentation.CurrentUser;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class CreditController implements Initializable {

    @FXML
    private Label emailHeader;
    @FXML
    private TextField emailPrompt;
    @FXML
    private Label firstNameHeader;
    @FXML
    private TextField firstNamePrompt;
    @FXML
    private Label middleNameHeader;
    @FXML
    private TextField middleNamePrompt;
    @FXML
    private Label lastNameHeader;
    @FXML
    private TextField lastNamePrompt;
    @FXML
    private Label creditTitleHeader;
    @FXML
    private TextField creaditTitlePrompt;
    @FXML
    private Button createCreditBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private UserEntity creditActor;


    @FXML
    private void createCredit() throws IOException {

    }
    @FXML
    private void updateCredit() throws IOException {

    }
    @FXML
    private void deleteCredit() throws IOException {

    }

    /**
     * Opens "createCredit.fxml" as a popup scene
     * @return a UserEntity with its different variables
     */
    public UserEntity openView() {
        FXMLLoader loader = null;
        try {
            loader = App.getLoader("createCredit");
            Parent node = loader.load();

            Scene scene = new Scene(node);
            CreditController creditController = loader.<CreditController>getController();
            Stage stage = new Stage();
            stage.setTitle(LanguageHandler.getText("createCreditStageTitle"));
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            return creditController.creditActor;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a UserEntity and sets its parameters to what has been entered in the text areas in the "createCredits" scene
     * @param event
     * @throws IOException
     */
    @FXML
    public void createActor(ActionEvent event) throws IOException {
        UserEntity actorCredit = new UserEntity(creaditTitlePrompt.getText(), firstNamePrompt.getText(),
                middleNamePrompt.getText(), lastNamePrompt.getText(), new Date(), emailPrompt.getText());
        actorCredit.setRole(Role.Actor);
        actorCredit.setCreatedBy(CurrentUser.getInstance().getUserEntity());
        creditActor = actorCredit;

        closeCreateCredit(event);
    }

    @FXML
    private void closeCreateCredit(ActionEvent event)
    {
        ControllerUtility.closeProgram(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailHeader.setText(LanguageHandler.getText("emailHeader"));
        emailPrompt.setPromptText(LanguageHandler.getText("emailPrompt"));
        firstNameHeader.setText(LanguageHandler.getText("firstName"));
        firstNamePrompt.setPromptText(LanguageHandler.getText("firstNamePrompt"));
        middleNameHeader.setText(LanguageHandler.getText("middleName"));
        middleNamePrompt.setPromptText(LanguageHandler.getText("middleNamePrompt"));
        lastNameHeader.setText(LanguageHandler.getText("lastName"));
        lastNamePrompt.setPromptText(LanguageHandler.getText("lastNamePrompt"));
        createCreditBtn.setText(LanguageHandler.getText("createCreditStageTitle"));
        creditTitleHeader.setText(LanguageHandler.getText("titleHeader"));
        creaditTitlePrompt.setPromptText(LanguageHandler.getText("creditTitlePrompt"));
    }
}
