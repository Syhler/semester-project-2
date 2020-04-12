package org.example.presentation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    public TextArea updateInsertTitle;
    public Label updateProgDescription;
    public TextArea updateInsertDescription;
    public Label updateProgCompany;
    public TextField updateInsertCompany;
    public Label updateProgCredits;
    public Button updateCreditBtn;
    public Button updateProgramBtn;
    public Label remainingCharactersDesc;
    public int maxSizeDesc = 500;
    public Label remainingCharactersTitle;
    public int maxSizeTitle = 100;
    public Label updateProgProducer;
    public TextField updateInsertProducer;


    @FXML
    private void closeUpdateProgram(ActionEvent event)
    {
        ControllerUtility.closeProgram(event);
    }

    @FXML
    private void remainingCharactersDesc()
    {
        updateInsertDescription.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                remainingCharactersDesc.setText(ControllerUtility.remainingCharacters(updateInsertDescription, maxSizeDesc));
            }
        });
    }

    @FXML
    private void remainingCharactersTitle()
    {
        updateInsertTitle.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                remainingCharactersTitle.setText(ControllerUtility.remainingCharacters(updateInsertTitle, maxSizeTitle));
            }
        });
    }

    @FXML
    public void goToCreateCredit(ActionEvent event) throws IOException {

        FXMLLoader loader = null;
        loader = App.getLoader("createCredit");
        Parent node = loader.load();
        //CreateCreditController createCreditController = loader.<CreateCreditController>getController();

        Scene scene = new Scene(node);

        Stage stage = new Stage();
        stage.setTitle(LanguageHandler.getText("createCreditStageTitle"));
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        remainingCharactersDesc();
        remainingCharactersDesc.setText(ControllerUtility.remainingCharacters(updateInsertDescription, maxSizeDesc));
        ControllerUtility.maxTextSize(updateInsertDescription, maxSizeDesc);

        remainingCharactersTitle();
        remainingCharactersTitle.setText(ControllerUtility.remainingCharacters(updateInsertTitle, maxSizeTitle));
        ControllerUtility.maxTextSize(updateInsertTitle, maxSizeTitle);


        updateProgramBtn.setText(LanguageHandler.getText("updateProgram"));
        cancelBtn.setText(LanguageHandler.getText("cancel"));
        updateProgTitle.setText(LanguageHandler.getText("titleHeader"));
        updateInsertTitle.setPromptText(LanguageHandler.getText("insertTitle"));
        updateProgDescription.setText(LanguageHandler.getText("descriptionHeader"));
        updateInsertDescription.setPromptText(LanguageHandler.getText("insertDescription"));
        updateProgCompany.setText(LanguageHandler.getText("programCompany"));
        updateInsertCompany.setPromptText(LanguageHandler.getText("insertCompany"));
        updateProgCredits.setText(LanguageHandler.getText("programCredits"));
        updateCreditBtn.setText(LanguageHandler.getText("createCreditStageTitle"));
        updateProgProducer.setText(LanguageHandler.getText("producer"));
        updateInsertProducer.setPromptText(LanguageHandler.getText("insertProducer"));
    }
}
