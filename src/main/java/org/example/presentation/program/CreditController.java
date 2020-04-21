package org.example.presentation.program;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.presentation.utilities.ControllerUtility;
import org.example.presentation.utilities.CurrentUser;
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
    private User creditActor;

    /**
     * Opens "createCredit.fxml" as a popup scene
     * @return a UserEntity with its different variables
     */
    public User openView(ActionEvent event) {
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
            stage.getIcons().add(new Image(App.class.getResourceAsStream("loginImages/tv2trans.png")));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getTarget()).getScene().getWindow());
            stage.showAndWait();
            return creditController.creditActor;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a UserEntity and sets its parameters to what has been entered in the text areas in the "createCredits" scene
     */
    @FXML
    public void createActor(ActionEvent event) {
        //TODO create company
        creditActor = new User(
                creaditTitlePrompt.getText(),
                firstNamePrompt.getText(),
                middleNamePrompt.getText(),
                lastNamePrompt.getText(),
                new Date(),
                emailPrompt.getText(),
                Role.Actor,
                CurrentUser.getInstance().getUser());

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
