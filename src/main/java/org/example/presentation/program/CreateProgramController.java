package org.example.presentation.program;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.domain.applicationFacade.DomainFacade;
import org.example.domain.buisnessComponents.Program;
import org.example.domain.buisnessComponents.ProgramInformation;
import org.example.presentation.utilities.ControllerUtility;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateProgramController implements Initializable {

    @FXML
    private ProgressIndicator progressIndicator;
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
    private Program program;


    private DomainFacade domainHandler = new DomainFacade();

    /**
     * Opens "createProgram.fxml" as a popup scene
     * @return ProgramEntity of the program created
     */
    public Program openView(ActionEvent event) throws IOException
    {

        FXMLLoader loader = App.getLoader("createProgram");
        Parent root = loader.load();

        CreateProgramController createProgramController = loader.getController();

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle(LanguageHandler.getText("createProgramStageTitle"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("loginImages/tv2trans.png")));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getTarget()).getScene().getWindow());
        stage.showAndWait();

        return createProgramController.program;
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
    public void createProgramOnAction(ActionEvent event) throws IOException
    {
        progressIndicator.setVisible(true);
        var thread = new Thread(createProgramAndCloseModal(event));
        thread.setDaemon(true);
        thread.start();

    }

    private Runnable createProgramAndCloseModal(ActionEvent event)
    {
        return () -> {
            ProgramInformation programInformation = new ProgramInformation(getTitle(), getDescription());
            program = domainHandler.createProgram(programInformation);
            progressIndicator.setVisible(false);

            UpdateProgramController updateProgramController = new UpdateProgramController();
            Platform.runLater(() ->
            {
                try {
                    this.program = updateProgramController.openView(program, event);
                    ControllerUtility.closeProgram(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        };
    }



    private String getTitle()
    {
        return insertTitle.getText();
    }

    private String getDescription()
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
