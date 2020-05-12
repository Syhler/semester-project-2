package org.example.presentation.usermangement;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.App;
import org.example.domain.applicationFacade.DomainFacade;
import org.example.domain.buisnessComponents.Program;
import org.example.domain.buisnessComponents.Role;
import org.example.domain.buisnessComponents.User;
import org.example.presentation.multipleLanguages.Language;
import org.example.presentation.multipleLanguages.LanguageHandler;
import org.example.presentation.program.CreateProgramController;
import org.example.presentation.program.ProgramListController;
import org.example.presentation.program.ProgramManagementController;
import org.example.presentation.utilities.ControllerUtility;
import org.example.presentation.utilities.CurrentUser;
import org.example.presentation.utilities.UsermanagementUtilities;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserManagementController implements Initializable {
    @FXML
    private ProgressIndicator progressIndicator;

    private final DomainFacade domainHandler = new DomainFacade();
    @FXML
    private Button closeWindow;
    @FXML
    private Button minimizeWindow;

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
    private ToggleButton displayDeletedActors;
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
    @FXML
    private Button unDeleteSelected;
    @FXML
    private ToggleButton programManagementBtn;


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
        displayDeletedActors.setText(LanguageHandler.getText("displayDeletedActors"));
        createPopup.setText(LanguageHandler.getText("createPopup"));
        editBtn.setText(LanguageHandler.getText("editBtn"));
        deleteSelected.setText(LanguageHandler.getText("deleteSelected"));
        login.setText(LanguageHandler.getText("logoff"));
        searchNavigation.setText(LanguageHandler.getText("searchNavigation"));
        profileNavigation.setText(LanguageHandler.getText("profile"));
        usermanagementBtn.setText(LanguageHandler.getText("usermanagementBtn"));
        createProgram.setText(LanguageHandler.getText("createProgram"));
        unDeleteSelected.setText(LanguageHandler.getText("unDelete"));

        usermanagementBtn.setSelected(true);
        companyAddToggle.setVisible(false);
        unDeleteSelected.setVisible(false);
        programManagementBtn.setVisible(false);


        switch (CurrentUser.getInstance().getUser().getRole()) {
            case Admin:
                displayAdmins.fire();
                displayAdmins.setSelected(true);
                companyAddToggle.setVisible(true);
                programManagementBtn.setVisible(true);
                break;
            case Manufacture:
                displayAdmins.setVisible(false);
                displayManufactures.setVisible(false);
                displayDeletedActors.setVisible(false);
                displayProducers.fire();
                displayProducers.setSelected(true);
                break;
            case Producer:
                displayAdmins.setVisible(false);
                displayManufactures.setVisible(false);
                displayProducers.setVisible(false);
                displayDeletedActors.setVisible(false);
                displayActors.fire();
                displayActors.setSelected(true);
                break;
            case Actor:
                displayAdmins.setVisible(false);
                displayManufactures.setVisible(false);
                displayProducers.setVisible(false);
                displayActors.setVisible(false);
                displayDeletedActors.setVisible(false);
                importBtn.setVisible(false);
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
                        ? user.getValue().getCreatedBy().getFullName()
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
            UsermanagementUtilities.setFeedback(event, user.getName().getFirstName()+LanguageHandler.getText("userCreated"), true);
        } else {
            UsermanagementUtilities.setFeedback(event, LanguageHandler.getText("userNotCreated"), false);

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
    private void openUpdateUser(ActionEvent event) throws InterruptedException {

        User userToUpdate = table.getSelectionModel().getSelectedItem();
        UpdateUserController updateUserController = new UpdateUserController();

        User user = updateUserController.openUpdateUser(event, userToUpdate);
        profileNavigation.setSelected(false);
        editBtn.setSelected(false);

        if (user != null) {
            userList.remove(userToUpdate);
            UsermanagementUtilities.setFeedback(event, user.getName().getFirstName()+LanguageHandler.getText("userUpdated"), true);


            if (user.getRole() == roleTap) {
                userList.add(user);
            }
        }
        // else {
        //    UsermanagementUtilities.setFeedback(event, LanguageHandler.getText("userNotUpdated"), false);
        // }
    }

    /**
     * Checks which togglebutton is active in the usermangementController and displays users acording to the button
     * @param event
     */
    @FXML
    private void displayByRole(ActionEvent event) {
        Object displayByRole = event.getSource();
        userList.clear();
        progressIndicator.setVisible(true);
        var thread = new Thread(displayByRoleRunnable(displayByRole));
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void setDisplayDeletedActors(ActionEvent event)
    {
        Object displayDeletedActors = event.getSource();
        userList.clear();
    }

    private Runnable displayByRoleRunnable(Object displayByRole)
    {
        return () ->
        {
            if (displayByRole == displayAdmins) {
                Platform.runLater(() ->{
                    displayManufactures.setSelected(false);
                    displayProducers.setSelected(false);
                    displayActors.setSelected(false);
                    displayDeletedActors.setSelected(false);
                    unDeleteSelected.setVisible(false);
                    deleteSelected.setVisible(true);
                    createPopup.setVisible(true);

                });
                userList.addAll(domainHandler.getUserByRole(Role.Admin));
                roleTap = Role.Admin;
            }
            if (displayByRole == displayManufactures) {
                Platform.runLater(() ->
                {
                    displayAdmins.setSelected(false);
                    displayProducers.setSelected(false);
                    displayActors.setSelected(false);
                    displayDeletedActors.setSelected(false);
                    unDeleteSelected.setVisible(false);
                    deleteSelected.setVisible(true);
                    createPopup.setVisible(true);
                });
                userList.addAll(domainHandler.getUserByRole(Role.Manufacture));
                roleTap = Role.Manufacture;
            }
            if (displayByRole == displayProducers) {
                Platform.runLater(() ->
                {
                    displayManufactures.setSelected(false);
                    displayAdmins.setSelected(false);
                    displayActors.setSelected(false);
                    displayDeletedActors.setSelected(false);
                    unDeleteSelected.setVisible(false);
                    deleteSelected.setVisible(true);
                    createPopup.setVisible(true);

                });

                if (CurrentUser.getInstance().getUser().getRole() == Role.Admin) {
                    userList.addAll(domainHandler.getUserByRole(Role.Producer));
                } else {
                    userList.addAll(domainHandler.getUserByCompany(CurrentUser.getInstance().getUser(), Role.Producer));
                }
                roleTap = Role.Producer;
            }
            if (displayByRole == displayActors) {
                Platform.runLater(() ->
                {
                    displayManufactures.setSelected(false);
                    displayProducers.setSelected(false);
                    displayAdmins.setSelected(false);
                    displayDeletedActors.setSelected(false);
                    unDeleteSelected.setVisible(false);
                    deleteSelected.setVisible(true);
                    createPopup.setVisible(true);

                });
                if (CurrentUser.getInstance().getUser().getRole() == Role.Admin) {
                    userList.addAll(domainHandler.getUserByRole(Role.Actor));
                } else {
                    userList.addAll(domainHandler.getUserByCompany(CurrentUser.getInstance().getUser(), Role.Actor));
                }
                roleTap = Role.Actor;
            }
            if (displayByRole == displayDeletedActors) {
                Platform.runLater(() ->{
                    displayManufactures.setSelected(false);
                    displayProducers.setSelected(false);
                    displayActors.setSelected(false);
                    displayAdmins.setSelected(false);
                    unDeleteSelected.setVisible(true);
                    deleteSelected.setVisible(false);
                    createPopup.setVisible(false);
                });
                userList.addAll(domainHandler.getDeletedUsers());
                roleTap = Role.Admin;
            }

            Platform.runLater(() -> {
                progressIndicator.setVisible(false);
                table.getSelectionModel().select(0);
            });

        };
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
            UsermanagementUtilities.setFeedback(event,selectedUser.getName().getFirstName()+" "+LanguageHandler.getText("userDeleted"),true);
        } else {
            UsermanagementUtilities.setFeedback(event,LanguageHandler.getText("userNotDeleted"),false);
        }
    }

    @FXML
    private void unDeleteUser(ActionEvent event)
    {
        User selectedUser = table.getSelectionModel().getSelectedItem();

        boolean userWasRemoved = selectedUser.unDelete();

        if (userWasRemoved) {
            userList.remove(selectedUser);
            UsermanagementUtilities.setFeedback(event,selectedUser.getName().getFirstName()+" "+LanguageHandler.getText("userReactivated"),true);
        } else {
            UsermanagementUtilities.setFeedback(event,selectedUser.getName().getFirstName()+" "+LanguageHandler.getText("userNotReactivated"),false);
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
        Program programEntity = createProgramController.openView(event);
        createProgram.setSelected(false);
        if (programEntity != null && programListController != null)
        {
            if (programListController.listOfPrograms != null)
            {
                programListController.listOfPrograms.add(programEntity);
                programListController.updateProgramList();
                UsermanagementUtilities.setFeedback(event,"The program was created",true);
            }
        } else {
            UsermanagementUtilities.setFeedback(event,"The program was not created",false);
        }
    }

    public void importOnAction(ActionEvent event) throws Exception {

        ControllerUtility.importProgram(event);
        importBtn.setSelected(false);
    }

    @FXML
    private void goToProgramManagement(ActionEvent event) throws IOException {
        ProgramManagementController programManagementController = new ProgramManagementController();
        programManagementBtn.setSelected(false);
        programManagementController.openView(event);
    }
}
