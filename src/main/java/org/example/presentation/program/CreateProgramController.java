package org.example.presentation.program;

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
import org.example.OLDdomain.DomainHandler;
import org.example.OLDentity.ProgramEntity;
import org.example.presentation.utilities.ControllerUtility;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateProgramController implements Initializable {
    @FXML
    private Button cancelCreateProgram;
    @FXML
    private Label createProgTitle;
    @FXML
    private Label createProgDescription;
    @FXML
    private TextArea insertTitle;
    @FXML
    private TextArea insertDescription;
    @FXML
    private Button createProgramBtn;
    @FXML
    private Label remainingCharactersDesc;
    @FXML
    private Label remainingCharactersTitle;

    private int maxSizeDesc = 1000;
    private int maxSizeTitle = 100;
    private ProgramEntity programEntity;


    private DomainHandler domainHandler = new DomainHandler();

    /**
     * Opens "createProgram.fxml" as a popup scene
     * @return ProgramEntity of the program created
     */
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
        stage.setResizable(false);
        stage.showAndWait();
        return createProgramController.programEntity;
    }

    @FXML
    private void closeCreateProgram(ActionEvent event)
    {
        ControllerUtility.closeProgram(event);
    }


    /**
     * On the click of a button, opens the update scene of the program that the user have clicked
     * @param event
     * @throws IOException
     */
    @FXML
    public void goToUpdateProgram(ActionEvent event) throws IOException {
        ProgramEntity programEntity = new ProgramEntity(getTitle(), getDescription(),null,null,null);
        long programId = domainHandler.program().createProgram(programEntity);
        programEntity.setId(programId);
        UpdateProgramController updateProgramController = new UpdateProgramController();
        this.programEntity = updateProgramController.openView(programEntity);

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

    /**
     * Calculates, updates and inserts how many characters the user has remaining in the description textArea
     */
    private void remainingCharactersDesc()
    {
        insertDescription.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                remainingCharactersDesc.setText(ControllerUtility.remainingCharacters(insertDescription, maxSizeDesc));
            }
        });
    }

    /**
     * Calculates, updates and inserts how many characters the user has remaining in the title textArea
     */
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
        createProgramBtn.setText(LanguageHandler.getText("createProgram"));
    }
}
