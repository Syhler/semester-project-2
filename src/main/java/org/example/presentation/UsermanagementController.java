package org.example.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.domain.DomainHandler;
import org.example.entity.Role;
import org.example.entity.UserEntity;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsermanagementController implements Initializable {
    private DomainHandler domainHandler = new DomainHandler();
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
    private Button editBtn;
    @FXML
    private Button deleteSelected;
    @FXML
    private Button login;
    @FXML
    private Button searchNavigation;

    @FXML
    private ToggleButton companyAddToggle;

    // Table objects
    @FXML
    private TableView<UserEntity> table;
    @FXML
    private TableColumn<UserEntity, String> col_id;
    @FXML
    private TableColumn<UserEntity, String> col_name;
    @FXML
    private TableColumn<UserEntity, String> col_company;
    @FXML
    private TableColumn<UserEntity, String> col_title;
    @FXML
    private TableColumn<UserEntity, String> col_createdBy;
    @FXML
    private TableColumn<UserEntity, String> col_created;


    private ObservableList<UserEntity> userList = FXCollections.observableArrayList();

    private Role roleTap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * Language
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

        companyAddToggle.setVisible(false);

        switch (CurrentUser.getInstance().getUserEntity().getRole()) {
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
        col_company.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_createdBy.setCellValueFactory(new PropertyValueFactory<>("createdByName"));
        col_created.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        table.setItems(userList);

        table.getSelectionModel().select(0);
    }

    @FXML
    private void openCreateUser(ActionEvent event) {
        CreateUserController createusercontroller = new CreateUserController();
        UserEntity user = createusercontroller.openCreateUser(event, roleTap);

        if (user != null) {
            userList.add(user);
        }
    }

    @FXML
    private void openCompanyController(ActionEvent event) {
        CompanyController companyController = new CompanyController();
        companyController.openCompanyController(event);
        companyAddToggle.setSelected(false);
    }

    @FXML
    private void openUpdateUser(ActionEvent event) {

        UserEntity userToUpdate = table.getSelectionModel().getSelectedItem();
        UpdateUserController updateUserController = new UpdateUserController();
        UserEntity user = updateUserController.openUpdateUser(event, userToUpdate, roleTap);

        if (user != null) {
            userList.remove(userToUpdate);

            if (user.getRole() == roleTap) {
                userList.add(user);
            }
        }
    }

    @FXML
    private void displayByRole(ActionEvent event) {
        Object displayByRole = event.getSource();
        userList.clear();

        if (displayByRole == displayAdmins) {
            userList.addAll(domainHandler.user().getUserByRole(Role.Admin));
            roleTap = Role.Admin;
            displayManufactures.setSelected(false);
            displayProducers.setSelected(false);
            displayActors.setSelected(false);
        }
        if (displayByRole == displayManufactures) {
            userList.addAll(domainHandler.user().getUserByRole(Role.Manufacture));
            roleTap = Role.Manufacture;
            displayAdmins.setSelected(false);
            displayProducers.setSelected(false);
            displayActors.setSelected(false);
        }
        if (displayByRole == displayProducers) {
            userList.addAll(domainHandler.user().getUserByRole(Role.Producer));
            roleTap = Role.Producer;
            displayManufactures.setSelected(false);
            displayAdmins.setSelected(false);
            displayActors.setSelected(false);
        }
        if (displayByRole == displayActors) {
            userList.addAll(domainHandler.user().getUserByRole(Role.Actor));
            roleTap = Role.Actor;
            displayManufactures.setSelected(false);
            displayProducers.setSelected(false);
            displayAdmins.setSelected(false);
        }

        table.getSelectionModel().select(0);
    }


    @FXML
    private void logout(ActionEvent event) throws IOException {
        if (CurrentUser.getInstance().getUserEntity() != null) {
            CurrentUser.getInstance().init(null); //Logs off
            App.setRoot("default");
        }
    }

    @FXML
    private void goToDefault(ActionEvent event) throws IOException {
        App.setRoot("default");
    }

    @FXML
    private void deleteUser(ActionEvent event) throws IOException {
        UserEntity selectedUser = table.getSelectionModel().getSelectedItem();

        if (domainHandler.user().removeUser(selectedUser)) {
            userList.remove(selectedUser);
        }
    }
}
