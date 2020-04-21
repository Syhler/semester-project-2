package org.example.presentation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import org.example.App;
import org.example.domain.DomainFacade;
import org.example.domain.Program;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.domain.io.Import;
import org.example.presentation.multipleLanguages.LanguageHandler;
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
    private ToggleButton login;
    @FXML
    private ToggleButton createProgram;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField searchBar;
    @FXML
    private ToolBar navigation;
    @FXML
    private ToggleButton importBtn;
    @FXML
    private ToggleButton usermanagementBtn;
    @FXML
    private ToggleButton profileNavigation;
    @FXML
    private ToggleButton searchNavigation;

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
            login.setSelected(false);

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
        createProgram.setSelected(false);
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
            importBtn.setSelected(false);
            return;
        }


        var loadedPrograms = Import.loadPrograms(selectedFile);


        if (loadedPrograms.isEmpty())
        {
            controller.openDialog(event, LanguageHandler.getText("noProgramsImported"), "Import Dialog");
            importBtn.setSelected(false);
        }
        else
        {
            loadedPrograms = domainHandler.importPrograms(loadedPrograms);

            controller.openDialog(event,
                    LanguageHandler.getText("succeedImport") + " " + loadedPrograms.size() + " " +
                            LanguageHandler.getText("programs"), "Import Dialog");
            ProgramListController.getInstance().listOfPrograms.addAll(loadedPrograms);
            ProgramListController.getInstance().updateProgramList();
            importBtn.setSelected(false);
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
            var programs = domainHandler.search(searchBar.getText());
            setImages(programs);
            ProgramListController.getInstance().listOfPrograms = programs;
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
            setImages(allPrograms);

            programListController.listOfPrograms.addAll(allPrograms);
            programListController.updateProgramList();
            borderPane.setCenter(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setImages(List<Program> programs)
    {
        var listOfImages = new ArrayList<Image>();
        listOfImages.add(new Image(App.class.getResourceAsStream("loginImages/kim.jpg")));
        listOfImages.add(new Image(App.class.getResourceAsStream("loginImages/mænd.jpg")));
        listOfImages.add(new Image(App.class.getResourceAsStream("loginImages/nah.jpg")));
        listOfImages.add(new Image(App.class.getResourceAsStream("loginImages/dal.jpg")));
        listOfImages.add(new Image(App.class.getResourceAsStream("loginImages/3.jpg")));

        for (var program : programs) {
            program.setImage(listOfImages.get(new Random().nextInt(listOfImages.size())));
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
        profileNavigation.setSelected(false);
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
        searchNavigation.setText(LanguageHandler.getText("searchNavigation"));
        navigation.prefWidthProperty().bind(borderPane.widthProperty());
        searchNavigation.setSelected(true);

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
