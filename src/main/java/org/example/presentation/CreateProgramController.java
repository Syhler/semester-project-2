package org.example.presentation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.App;
import org.example.entity.ProgramEntity;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateProgramController implements Initializable {
    public Button cancelCreateProgram;
    public Label createProgTitle;
    public Label createProgDescription;
    public TextArea insertTitle;
    public TextArea insertDescription;
    public Button createProgBtn;
    public Label remainingCharactersDesc;
    public Label remainingCharactersTitle;
    public int maxSizeDesc = 500;
    public int maxSizeTitle = 100;
    public ProgramEntity programEntity;


    public ProgramEntity openView()
    {
        Parent root = null;
        FXMLLoader loader = null;

        try {
            loader = App.getLoader("createProgram");
            root = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        CreateProgramController createProgramController = loader.<CreateProgramController>getController();

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle(LanguageHandler.getText("createProgramStageTitle"));
        stage.setScene(scene);
        stage.showAndWait();
        return createProgramController.programEntity;
    }

    @FXML
    private void closeCreateProgram(ActionEvent event)
    {
        ControllerUtility.closeProgram(event);
    }



    @FXML
    public void goToUpdateProgram(ActionEvent event) throws IOException {
        UpdateProgramController updateProgramController = new UpdateProgramController();
        programEntity = updateProgramController.openView(getTitle(), getDescription());

        closeCreateProgram(event);
    }

    public String getTitle()
    {
        return insertTitle.getText();
    }

    public String getDescription()
    {
        return insertDescription.getText();
    }


    private void remainingCharactersDesc()
    {
        insertDescription.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                remainingCharactersDesc.setText(ControllerUtility.remainingCharacters(insertDescription, maxSizeDesc));
            }
        });
    }
    private void remainingCharactersTitle()
    {
        insertTitle.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                remainingCharactersTitle.setText(ControllerUtility.remainingCharacters(insertTitle, maxSizeTitle));
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        remainingCharactersDesc();
        remainingCharactersDesc.setText(ControllerUtility.remainingCharacters(insertDescription, maxSizeDesc));
        ControllerUtility.maxTextSize(insertDescription, maxSizeDesc);

        remainingCharactersTitle();
        remainingCharactersTitle.setText(ControllerUtility.remainingCharacters(insertTitle, maxSizeTitle));
        ControllerUtility.maxTextSize(insertTitle, maxSizeTitle);

        createProgTitle.setText(LanguageHandler.getText("titleHeader"));
        createProgDescription.setText(LanguageHandler.getText("descriptionHeader"));
        cancelCreateProgram.setText(LanguageHandler.getText("cancel"));
        insertTitle.setPromptText(LanguageHandler.getText("insertTitle"));
        insertDescription.setPromptText(LanguageHandler.getText("insertDescription"));
        createProgBtn.setText(LanguageHandler.getText("createProgram"));
    }
}
