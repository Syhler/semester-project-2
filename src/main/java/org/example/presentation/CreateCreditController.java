package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateCreditController implements Initializable {
    public Label firstNameHeader;
    public TextField firstNamePrompt;
    public Label middleNameHeader;
    public TextField middleNamePrompt;
    public Label lastNameHeader;
    public TextField lastNamePrompt;
    public Label creditTitleHeader;
    public TextField creaditTitlePrompt;
    public Button createCreditBtn;
    public Button cancelBtn;


    @FXML
    private void closeCreateCredit(ActionEvent event)
    {
        ControllerUtility.closeProgram(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
