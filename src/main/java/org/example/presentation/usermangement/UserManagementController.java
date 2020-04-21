package org.example.presentation.usermangement;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.App;
import org.example.domain.DomainFacade;
import org.example.domain.Program;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.domain.io.Import;
import org.example.presentation.dialogControllers.ImportExportDialogController;
import org.example.presentation.multipleLanguages.LanguageHandler;
import org.example.presentation.program.CreateProgramController;
import org.example.presentation.program.ProgramListController;
import org.example.presentation.utilities.CurrentUser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserManagementController implements Initializable {
    private DomainFacade domainHandler = new DomainFacade();
    // Function Buttons Create, Read, Update, Delete
    @FXML
    private ToggleButton displayAdmins;
    @FXML
    private ToggleButton displayManufactures;
    @FXML
    private ToggleButton displayProducers;
    @FXML
    private ToggleButton displayActors;
    @FXML
    private Button createPopup;
    @FXML
    private ToggleButton editBtn;
    @FXML
    private Button deleteSelected;
    @FXML
    private ToggleButton login;
    @FXML
    private ToggleButton searchNavigation;
    @FXML
    private ToggleButton createProgram;
    @FXML
    private ToggleButton importBtn;
    @FXML
    private ToggleButton usermanagementBtn;
    @FXML
    private ToggleButton profileNavigation;
    @FXML
    private ToggleButton companyAddToggle;

    // Table objects
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> col_id;
    @FXML
    private TableColumn<User, String> col_name;
    @FXML
    private TableColumn<User, String> col_company;
    @FXML
    private TableColumn<User, String> col_title;
    @FXML
    private TableColumn<User, String> col_createdBy;
    @FXML
    private TableColumn<User, String> col_created;


    private ObservableList<User> userList = FXCollections.observableArrayList();
    private ProgramListController programListController;
    private Role roleTap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * Language for all buttons, labels, feedback texts etc
         */
        col_name.setText(LanguageHandler.getText("nameColumn"));
        col_company.setText(LanguageHandler.getText("companyColumn"));
        col_title.setText(LanguageHandler.getText("titleColumn"));
        col_createdBy.setText(LanguageHandler.getText("createdbyColumn"));
        col_created.setText(LanguageHandler.getText("createdColumn"));
        companyAddToggle.setText(LanguageHandler.getText("companyBtn"));
        displayAdmins.setText(LanguageHandler.getText("displayAdmins"));
        displayManufactures.setText(LanguageHandler.getText("displayManufactures"));
        displayProducers.setText(LanguageHandler.getText("displayProducers"));
        displayActors.setText(LanguageHandler.getText("displayActors"));
        createPopup.setText(LanguageHandler.getText("createPopup"));
        editBtn.setText(LanguageHandler.getText("editBtn"));
        deleteSelected.setText(LanguageHandler.getText("deleteSelected"));
        login.setText(LanguageHandler.getText("logoff"));
        searchNavigation.setText(LanguageHandler.getText("searchNavigation"));
        login.setText(LanguageHandler.getText("login"));
        profileNavigation.setText(LanguageHandler.getText("profile"));
        usermanagementBtn.setText(LanguageHandler.getText("usermanagementBtn"));
        createProgram.setText(LanguageHandler.getText("createProgram"));

        usermanagementBtn.setSelected(true);
        companyAddToggle.setVisible(false);

        switch (CurrentUser.getInstance().getUser().getRole()) {
            case Admin:
                displayAdmins.fire();
                displayAdmins.setSelected(true);
                companyAddToggle.setVisible(true);
                break;
            case Manufacture:
                displayAdmins.setVisible(false);
                displayManufactures.setVisible(false);
                displayProducers.fire();
                displayProducers.setSelected(true);
                break;
            case Producer:
                displayAdmins.setVisible(false);
                displayManufactures.setVisible(false);
                displayProducers.setVisible(false);
                displayActors.fire();
                displayActors.setSelected(true);
                break;
            case Actor:
                displayAdmins.setVisible(false);
                displayManufactures.setVisible(false);
                displayProducers.setVisible(false);
                displayActors.setVisible(false);
                break;
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        col_company.setCellValueFactory(user -> new SimpleStringProperty(
                (user.getValue().getCompany() != null)
                        ? user.getValue().getCompany().getName()
                        : "" ));

        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));

        col_createdBy.setCellValueFactory(user -> new SimpleStringProperty(
                (user.getValue().getCreatedBy() != null)
                        ? user.getValue().getFullName()
                        : "" ));

        col_created.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        table.setItems(userList);

        table.getSelectionModel().select(0);
    }

    /**
     * Opens CreateUserController and Fxml
     * @param event
     */
    @FXML
    private void openCreateUser(ActionEvent event) {
        CreateUserController createusercontroller = new CreateUserController();
        User user = createusercontroller.openCreateUser(event, roleTap);

        if (user != null) {
            userList.add(user);
        }
    }

    /**
     * Opens CompanyController and Fxml
     * @param event
     */
    @FXML
    private void openCompanyController(ActionEvent event) {
        CompanyController companyController = new CompanyController();
        companyController.openCompanyController(event);
        companyAddToggle.setSelected(false);

    }

    /**
     * Opens UpdateUserController and Fxml
     * @param event
     * @return Updated UserEntity
     */
    @FXML
    private void openUpdateUser(ActionEvent event) {

        User userToUpdate = table.getSelectionModel().getSelectedItem();
        UpdateUserController updateUserController = new UpdateUserController();

        User user = updateUserController.openUpdateUser(event, userToUpdate);
        profileNavigation.setSelected(false);
        editBtn.setSelected(false);

        if (user != null) {
            userList.remove(userToUpdate);


            if (user.getRole() == roleTap) {
                userList.add(user);
            }
        }
    }

    /**
     * Checks which togglebutton is active in the usermangementController and displays users acording to the button
     * @param event
     */
    @FXML
    private void displayByRole(ActionEvent event) {
        Object displayByRole = event.getSource();
        userList.clear();

        if (displayByRole == displayAdmins) {
            userList.addAll(domainHandler.getUserByRole(Role.Admin));
            roleTap = Role.Admin;
            displayManufactures.setSelected(false);
            displayProducers.setSelected(false);
            displayActors.setSelected(false);
        }
        if (displayByRole == displayManufactures) {
            userList.addAll(domainHandler.getUserByRole(Role.Manufacture));
            roleTap = Role.Manufacture;
            displayAdmins.setSelected(false);
            displayProducers.setSelected(false);
            displayActors.setSelected(false);
        }
        if (displayByRole == displayProducers) {
            userList.addAll(domainHandler.getUserByRole(Role.Producer));
            roleTap = Role.Producer;
            displayManufactures.setSelected(false);
            displayAdmins.setSelected(false);
            displayActors.setSelected(false);
        }
        if (displayByRole == displayActors) {
            userList.addAll(domainHandler.getUserByRole(Role.Actor));
            roleTap = Role.Actor;
            displayManufactures.setSelected(false);
            displayProducers.setSelected(false);
            displayAdmins.setSelected(false);
        }

        table.getSelectionModel().select(0);
    }

    /**
     * Logs out the current user sets CurrentUser to null
     * @param event
     * @throws IOException
     */
    @FXML
    private void logout(ActionEvent event) throws IOException {
        if (CurrentUser.getInstance().getUser() != null) {
            CurrentUser.getInstance().init(null); //Logs off
            App.setRoot("default");
        }
    }

    /**
     * Changes to default controller and fxml (start screen)
     * @param event
     * @throws IOException
     */
    @FXML
    private void goToDefault(ActionEvent event) throws IOException {
        App.setRoot("default");
    }

    /**
     * Delets the selected user, removes from list if succesfull
     * @param event
     * @throws IOException
     */
    @FXML
    private void deleteUser(ActionEvent event) throws IOException {
        User selectedUser = table.getSelectionModel().getSelectedItem();

        boolean userWasRemoved = selectedUser.delete();

        if (userWasRemoved) {
            userList.remove(selectedUser);
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

        importBtn.setSelected(false);


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
}
