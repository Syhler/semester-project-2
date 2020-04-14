package org.example.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.example.App;
import org.example.domain.DomainHandler;
import org.example.entity.Role;
import org.example.entity.UserEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsermanagementController implements Initializable {
    private DomainHandler domainHandler = new DomainHandler();

    @FXML
    private Label createRole;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputCompany;
    @FXML
    private TextField inputTitle;

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

    public ObservableList<UserEntity> userList = FXCollections.observableArrayList();

    public int roleTap = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        companyAddToggle.setVisible(false);

        switch (CurrentUser.getInstance().getUserEntity().getRole().getValue()) {
            case 1:
                displayAdmins.fire();
                displayAdmins.setSelected(true);
                companyAddToggle.setVisible(true);
                break;
            case 2:
                displayAdmins.setVisible(false);
                displayManufactures.setVisible(false);
                displayProducers.fire();
                displayProducers.setSelected(true);
                break;
            case 3:
                displayAdmins.setVisible(false);
                displayManufactures.setVisible(false);
                displayProducers.setVisible(false);
                displayActors.fire();
                displayActors.setSelected(true);
                break;
            case 4:
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

            if (user.getRole().getValue() == roleTap) {
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
            roleTap = 1;
            displayManufactures.setSelected(false);
            displayProducers.setSelected(false);
            displayActors.setSelected(false);
        }
        if (displayByRole == displayManufactures) {
            userList.addAll(domainHandler.user().getUserByRole(Role.Manufacture));
            roleTap = 2;
            displayAdmins.setSelected(false);
            displayProducers.setSelected(false);
            displayActors.setSelected(false);
        }
        if (displayByRole == displayProducers) {
            userList.addAll(domainHandler.user().getUserByRole(Role.Producer));
            roleTap = 3;
            displayManufactures.setSelected(false);
            displayAdmins.setSelected(false);
            displayActors.setSelected(false);
        }
        if (displayByRole == displayActors) {
            userList.addAll(domainHandler.user().getUserByRole(Role.Actor));
            roleTap = 4;
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
