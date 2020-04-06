package org.example.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private Button displayAdmins;
    @FXML
    private Button displayManufactures;
    @FXML
    private Button displayProducers;
    @FXML
    private Button displayActors;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        col_company.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        //  col_createdBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        table.setItems(userList);


    }


    @FXML
    private void displayByRole(ActionEvent event){
        Object displayByRole = event.getSource();

        if (displayByRole == displayAdmins){
            createPopup.setText("Create: Admin");
            userList.clear();
            userList.addAll(domainHandler.user().getUserByRole(Role.Admin));

            System.out.println(userList);

        }

        if (displayByRole == displayManufactures){
            createPopup.setText("Create: Manufacture");
            userList.clear();
            userList.addAll(domainHandler.user().getUserByRole(Role.Manufacture));

            System.out.println(userList);

        }
        if (displayByRole == displayProducers){
            createPopup.setText("Create: Producer");
            userList.clear();
            userList.addAll(domainHandler.user().getUserByRole(Role.Producer));

            System.out.println(userList);

        }
        if (displayByRole == displayActors){
            createPopup.setText("Create: Actor");
            userList.clear();
            userList.addAll(domainHandler.user().getUserByRole(Role.Actor));

            System.out.println(userList);

        }

    }

    @FXML
    private void populateTable(){

    }

    @FXML
    private void createUser() throws IOException {
        //UserEntity newUser = new UserEntity();
        //domainHandler.user().createUser(newUser);
    }
    @FXML
    private void deleteUser() throws IOException {

    }
    @FXML
    private void updateUser() throws IOException {

    }


}
