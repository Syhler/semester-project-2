package org.example.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.App;
import org.example.domain.DomainHandler;
import org.example.entity.Role;
import org.example.entity.UserEntity;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
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
    private Button  deleteSelected;

    @FXML
    private HBox optionsHbox;

    // Logout
    //@FXML
    private Button login;

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

    // Table objects
    @FXML
    private TableView<UserEntity> table;
    @FXML
    private TableColumn<UserEntity,String> col_id;
    @FXML
    private TableColumn<UserEntity,String> col_name;
    @FXML
    private TableColumn<UserEntity,String> col_company;
    @FXML
    private TableColumn<UserEntity,String> col_title;
    @FXML
    private TableColumn<UserEntity,String> col_createdBy;

    public ObservableList<UserEntity> userList = FXCollections.observableArrayList();

    public int roleTap = 0;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Hiding options bar
        optionsHbox.setVisible(false);

        switch (CurrentUser.getInstance().getUserEntity().getRole().getValue())
        {
            case 1:
                displayAdmins.fire();
                displayAdmins.setSelected(true);
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
                System.out.println("Special case, only edit of actor himself.");
                break;
        }





        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        col_company.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_createdBy.setCellValueFactory(new PropertyValueFactory<>("createdByName"));
        table.setItems(userList);


    }

    @FXML
    private void openCreateUser(ActionEvent event)
    {
        CreateUserController createusercontroller = new CreateUserController();
        UserEntity user = createusercontroller.openCreateUser(event,roleTap);
        if (user != null)
        {
            userList.add(user);
        }
    }

    @FXML
    private void openUpdateUser(ActionEvent event)
    {

        UserEntity userToUpdate = table.getSelectionModel().getSelectedItem();
        UpdateUserController updateUserController = new UpdateUserController();
        UserEntity user = updateUserController.openUpdateUser(event,userToUpdate, roleTap);


        if (user != null)
        {
            userList.remove(userToUpdate);
            userList.add(user);
        }
    }


    @FXML
    private void displayByRole(ActionEvent event){
        Object displayByRole = event.getSource();
        optionsHbox.setVisible(true);

        if (displayByRole == displayAdmins){
            userList.clear();
            userList.addAll(domainHandler.user().getUserByRole(Role.Admin));
            roleTap = 1;
            displayManufactures.setSelected(false);
            displayProducers.setSelected(false);
            displayActors.setSelected(false);
        }
        if (displayByRole == displayManufactures)
        {
            userList.clear();
            userList.addAll(domainHandler.user().getUserByRole(Role.Manufacture));
            roleTap = 2;
            displayAdmins.setSelected(false);
            displayProducers.setSelected(false);
            displayActors.setSelected(false);
        }
        if (displayByRole == displayProducers)
        {
            userList.clear();
            userList.addAll(domainHandler.user().getUserByRole(Role.Producer));
            roleTap = 3;
            displayManufactures.setSelected(false);
            displayAdmins.setSelected(false);
            displayActors.setSelected(false);
        }
        if (displayByRole == displayActors)
        {
            userList.clear();
            userList.addAll(domainHandler.user().getUserByRole(Role.Actor));
            roleTap = 4;
            displayManufactures.setSelected(false);
            displayProducers.setSelected(false);
            displayAdmins.setSelected(false);
        }

    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        if (CurrentUser.getInstance().getUserEntity() != null)
        {
            CurrentUser.getInstance().init(null); //Logs off
            App.setRoot("default");

        }
    }




    @FXML
    private void createUser() throws IOException {
        //UserEntity newUser = new UserEntity();
        //domainHandler.user().createUser(newUser);
    }
    @FXML
    private void deleteUser(ActionEvent event) throws IOException {
        UserEntity selectedUser = table.getSelectionModel().getSelectedItem();

        if (domainHandler.user().removeUser(selectedUser)){
            userList.remove(selectedUser);
        }


    }
    @FXML
    private void updateUser() throws IOException {

    }


}
