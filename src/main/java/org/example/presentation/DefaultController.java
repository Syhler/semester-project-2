package org.example.presentation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import org.example.App;
import org.example.domain.*;
import org.example.domain.io.Import;
import org.example.presentation.multipleLanguages.Language;
import org.example.presentation.multipleLanguages.LanguageHandler;
import org.example.presentation.multipleLanguages.LanguageModel;
import org.example.presentation.multipleLanguages.LanguageSelector;
import org.example.presentation.program.CreateProgramController;
import org.example.presentation.program.ProgramListController;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.presentation.dialogControllers.ImportExportDialogController;
import org.example.presentation.usermangement.UpdateUserController;
import org.example.presentation.utilities.CurrentUser;

public class DefaultController implements Initializable
{
    @FXML
    public ComboBox<LanguageModel> selectLanguage;
    @FXML
    private Button login;
    @FXML
    private Button createProgram;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField searchBar;
    @FXML
    private ToolBar navigation;
    @FXML
    private Button importBtn;
    @FXML
    private Button usermanagementBtn;
    @FXML
    private Button profileNavigation;

    private int sprog;

    private DomainFacade domainHandler = new DomainFacade();
    private ProgramListController programListController;




    /**
     * Opens AuthenticationController for login, closes if login was succesfull
     * @param event
     */
    @FXML
    private void goToLogin(ActionEvent event) {
        AuthenticationController authenticationController = new AuthenticationController();
        var userEntity = authenticationController.openLoginStage(event);

        if (userEntity != null) {
            login.setText(LanguageHandler.getText("logoff"));
            login.setOnAction(this::logout);
            profileNavigation.setVisible(true);

            if (userEntity.getRole() != Role.Actor) {
                usermanagementBtn.setVisible(true);
                createProgram.setVisible(true);
            }

            if (userEntity.getRole() == Role.Admin)
            {
                importBtn.setVisible(true);
            }
        }
    }

    /**
     * Logs out the current user sets CurrentUser to null
     * @param event
     * @throws IOException
     */
    private void logout(ActionEvent event) {
        if (CurrentUser.getInstance().getUser() != null) {
            CurrentUser.getInstance().init(null); //Logs off
            login.setText(LanguageHandler.getText("login"));
            login.setOnAction(this::goToLogin);

            usermanagementBtn.setVisible(false);
            profileNavigation.setVisible(false);
            createProgram.setVisible(false);
            importBtn.setVisible(false);
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
        Program programEntity = createProgramController.openView();
        if (programEntity != null)
        {
            programListController.listOfPrograms.add(programEntity);
            programListController.updateProgramList();
        }
    }

    public void importOnAction(ActionEvent event) throws Exception {
        var selectedFile = getFileFromFileChoose();

        ImportExportDialogController controller = new ImportExportDialogController();

        if (selectedFile == null)
        {
            controller.openDialog(event, LanguageHandler.getText("noFile"), "Import Dialog");
            return;
        }


        var loadedPrograms = Import.loadPrograms(selectedFile);


        if (loadedPrograms.isEmpty())
        {
            controller.openDialog(event, LanguageHandler.getText("noProgramsImported"), "Import Dialog");
        }
        else
        {
            loadedPrograms = domainHandler.importPrograms(loadedPrograms);

            controller.openDialog(event,
                    LanguageHandler.getText("succeedImport") + " " + loadedPrograms.size() + " " +
                            LanguageHandler.getText("programs"), "Import Dialog");
            ProgramListController.getInstance().listOfPrograms.addAll(loadedPrograms);
            ProgramListController.getInstance().updateProgramList();
        }
    }

    /**
     * open a fileChooser and return the file
     * @return the file the user have chosen from the file chooser
     */
    private File getFileFromFileChoose()
    {
        var fileChooserStage = new Stage();

        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));

        return fileChooser.showOpenDialog(fileChooserStage);
    }

    @FXML
    public void searchOnKeyPressed(KeyEvent keyEvent) throws Exception {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
        {
            ProgramListController.getInstance().listOfPrograms = domainHandler.search(searchBar.getText());
            ProgramListController.getInstance().updateProgramList();
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

            var allPrograms = domainHandler.getAllPrograms();

            programListController.listOfPrograms.addAll(allPrograms);
            programListController.updateProgramList();
            borderPane.setCenter(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToUserManagement() throws IOException {
        App.setRoot("usermanagement");
    }

    /**
     * Opens updateuser so user can update his/her own interformation only
     * @param event
     */
    @FXML
    private void openUpdateUser(ActionEvent event) {
        User userToUpdate = CurrentUser.getInstance().getUser();
        UpdateUserController updateUserController = new UpdateUserController();
        User user = updateUserController.openUpdateUser(event, userToUpdate);

        if (user != null) {
            CurrentUser.getInstance().init(user);
        }

    }
    public void chooseLanguage()
    {
        LanguageModel danish = new LanguageModel(new Image(App.class.getResourceAsStream("loginImages/loginUserIcon.png")), Language.Danish);
        LanguageModel english = new LanguageModel(new Image(App.class.getResourceAsStream("loginImages/tv2trans.png")), Language.English);
        ObservableList<LanguageModel> options = FXCollections.observableArrayList();
        options.addAll(danish, english);
        selectLanguage.setItems(options);
        selectLanguage.setCellFactory(c -> new LanguageSelector());
        selectLanguage.setButtonCell(new LanguageSelector());
    }

    public void setLanguage()
    {
        selectLanguage.valueProperty().addListener(new ChangeListener<LanguageModel>() {
            @Override
            public void changed(ObservableValue<? extends LanguageModel> observableValue, LanguageModel languageModel, LanguageModel t1) {
                if (t1 != null && t1.getLanguage() != null) {
                    LanguageHandler.initLanguage(t1.getLanguage());
                    setButtonLanguage();
                }
            }
        });
    }

    private void setButtonLanguage()
    {
        login.setText(LanguageHandler.getText("login"));
        profileNavigation.setText(LanguageHandler.getText("profile"));
        usermanagementBtn.setText(LanguageHandler.getText("usermanagementBtn"));
        createProgram.setText(LanguageHandler.getText("createProgram"));
        searchBar.setPromptText(LanguageHandler.getText("searchBarPrompt"));
    }

    private void currentLanguage()
    {
        for (int i = 0; i < selectLanguage.getItems().size(); i++) {

            if (selectLanguage.getItems().get(i).getLanguage() == LanguageHandler.getLanguage())
            {
                selectLanguage.getSelectionModel().select(i);
                break;
            }
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setLanguage();
        chooseLanguage();
        currentLanguage();

        /**
         * Language
         */
        setButtonLanguage();
        navigation.prefWidthProperty().bind(borderPane.widthProperty());

        if (CurrentUser.getInstance().getUser() != null) {
            login.setText(LanguageHandler.getText("logoff"));
            login.setOnAction(this::logout);
            profileNavigation.setVisible(true);

            if (CurrentUser.getInstance().getUser().getRole() != Role.Actor) {
                usermanagementBtn.setVisible(true);
                createProgram.setVisible(true);
            }

            if (CurrentUser.getInstance().getUser().getRole() == Role.Admin)
            {
                importBtn.setVisible(true);
            }
        }

        loadProgramList();
    }



}
