package org.example.presentation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.App;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateProgramController implements Initializable {

    public Button cancelBtn;
    public Label updateProgTitle;
    public TextField updateInsertTitle;
    public Label updateProgDescription;
    public TextArea updateInsertDescription;
    public Label updateProgCompany;
    public TextField updateInsertCompany;
    public Label updateProgCredits;
    public Button updateCreditBtn;
    public Button updateProgramBtn;
    public int maxSize = 500;
    public Label remainingCharacters;

    @FXML
    private void closeUpdateProgram(ActionEvent event)
    {
        ControllerUtility.closeProgram(event);
    }

    @FXML
    private void remainingCharacters()
    {
        updateInsertDescription.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                remainingCharacters.setText(ControllerUtility.remainingCharacters(updateInsertDescription, maxSize));
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        remainingCharacters();
        remainingCharacters.setText(ControllerUtility.remainingCharacters(updateInsertDescription, maxSize));
        ControllerUtility.maxDescriptionSize(updateInsertDescription, maxSize);
        updateProgramBtn.setText(LanguageHandler.getText("updateProgram"));
        cancelBtn.setText(LanguageHandler.getText("cancel"));
        updateProgTitle.setText(LanguageHandler.getText("titleHeader"));
        updateInsertTitle.setPromptText(LanguageHandler.getText("insertTitle"));
        updateProgDescription.setText(LanguageHandler.getText("descriptionHeader"));
        updateInsertDescription.setPromptText(LanguageHandler.getText("insertDescription"));
        updateProgCompany.setText(LanguageHandler.getText("programCompany"));
        updateInsertCompany.setPromptText(LanguageHandler.getText("insertCompany"));
        updateProgCredits.setText(LanguageHandler.getText("programCredits"));
        updateCreditBtn.setText(LanguageHandler.getText("updateCredits"));

    }
}
