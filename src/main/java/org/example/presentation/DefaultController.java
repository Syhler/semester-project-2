package org.example.presentation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import org.example.App;
import org.example.domain.DomainHandler;
import org.example.entity.ProgramEntity;
import org.example.entity.Role;
import org.example.entity.UserEntity;
import org.example.presentation.multipleLanguages.LanguageHandler;
import org.example.presentation.program.CreateProgramController;
import org.example.presentation.program.ProgramListController;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.domain.Import;
import org.example.presentation.dialogControllers.ImportExportDialogController;

public class DefaultController implements Initializable
{

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

    private DomainHandler domainHandler = new DomainHandler();
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
        if (CurrentUser.getInstance().getUserEntity() != null) {
            CurrentUser.getInstance().init(null); //Logs off
            login.setText(LanguageHandler.getText("login"));
            login.setOnAction(this::goToLogin);

            usermanagementBtn.setVisible(false);
            profileNavigation.setVisible(false);
            createProgram.setVisible(false);
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
            controller.openDialog(event,
                    LanguageHandler.getText("succeedImport") + " " + loadedPrograms.size() + " " +
                            LanguageHandler.getText("programs"), "Import Dialog");
        }

        ProgramListController.getInstance().programEntityList.addAll(loadedPrograms);
        ProgramListController.getInstance().updateProgramList();
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
            System.out.println("searching");
            var programs = domainHandler.program().search(searchBar.getText());
            ProgramListController.getInstance().programEntityList = programs;
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
            var allPrograms = domainHandler.program().getAllPrograms();
            programListController.programEntityList.addAll(allPrograms);
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
        UserEntity userToUpdate = CurrentUser.getInstance().getUserEntity();
        UpdateUserController updateUserController = new UpdateUserController();
        UserEntity user = updateUserController.openUpdateUser(event, userToUpdate, userToUpdate.getRole());

        if (user != null) {
            CurrentUser.getInstance().init(user);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * Language
         */
        login.setText(LanguageHandler.getText("login"));
        profileNavigation.setText(LanguageHandler.getText("profile"));
        usermanagementBtn.setText(LanguageHandler.getText("usermanagementBtn"));
        createProgram.setText(LanguageHandler.getText("createProgram"));
        searchBar.setPromptText(LanguageHandler.getText("searchBarPrompt"));
        navigation.prefWidthProperty().bind(borderPane.widthProperty());

        if (CurrentUser.getInstance().getUserEntity() != null) {
            login.setText(LanguageHandler.getText("logoff"));
            login.setOnAction(this::logout);
            profileNavigation.setVisible(true);

            if (CurrentUser.getInstance().getUserEntity().getRole() != Role.Actor) {
                usermanagementBtn.setVisible(true);
                createProgram.setVisible(true);
            }

            if (CurrentUser.getInstance().getUserEntity().getRole() == Role.Admin)
            {
                importBtn.setVisible(true);
            }
        }

        loadProgramList();
    }



}
