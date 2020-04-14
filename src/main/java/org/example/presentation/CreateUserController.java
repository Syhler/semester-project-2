package org.example.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.App;
import org.example.domain.DomainHandler;
import org.example.entity.CompanyEntity;
import org.example.entity.UserEntity;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable {

    private DomainHandler domainHandler = new DomainHandler();
    @FXML
    private TextField firstname;
    @FXML
    private TextField middelname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField email;
    @FXML
    private TextField title;
    @FXML
    private TextField password;

    @FXML
    private ComboBox<CompanyEntity> companyList;

    @FXML
    private Label statusText;

    public ObservableList<CompanyEntity> companyEntities = FXCollections.observableArrayList();

    private String rolename = "";
    private int roleValue;

    private UserEntity user = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Callback<ListView<CompanyEntity>, ListCell<CompanyEntity>> cellFactory = new Callback<ListView<CompanyEntity>, ListCell<CompanyEntity>>() {

            @Override
            public ListCell<CompanyEntity> call(ListView<CompanyEntity> l) {
                return new ListCell<CompanyEntity>() {

                    @Override
                    protected void updateItem(CompanyEntity item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        };

        companyList.setCellFactory(cellFactory);
        companyList.setButtonCell(cellFactory.call(null));
        companyEntities.addAll(domainHandler.company().getCompanies());
        companyList.setItems(companyEntities);

    }


    /**
     * Opens the CreateUser page and waits until the user is created or cancel
     *
     * @return currentUser
     */
    public UserEntity openCreateUser(ActionEvent event, int role) {
        switch (role) {
            case 1:
                rolename = "Admin";
                break;
            case 2:
                rolename = "Manufacture";
                break;
            case 3:
                rolename = "Producer";
                break;
            case 4:
                rolename = "Actor";
                break;
        }

        var createUserStage = new Stage();

        try {
            FXMLLoader myLoader = App.getLoader("userManagementCreate");
            createUserStage.setScene(new Scene(myLoader.load()));
            CreateUserController createusercontrol = myLoader.getController();
            createusercontrol.roleValue = role;

            createUserStage.setTitle("Create " + rolename);
            createUserStage.initModality(Modality.WINDOW_MODAL);
            createUserStage.initOwner(((Node) event.getTarget()).getScene().getWindow());
            createUserStage.setResizable(false);
            createUserStage.showAndWait();

            return createusercontrol.user;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    public void createUserFromInput(ActionEvent event) throws IOException {
        if (firstname.getText().isEmpty()) {
            setStatusText("Firstname is empty");
            return;
        } else if (lastname.getText().isEmpty()) {
            setStatusText("Lastname is empty");
            return;
        } else if (email.getText().isEmpty()) {
            setStatusText("Email is empty");
            return;
        } else if (companyList.getSelectionModel().isEmpty()) {
            setStatusText("A company must be selected");
            return;
        } else if (title.getText().isEmpty()) {
            setStatusText("Title is empty");
            return;
        } else if (password.getText().isEmpty()) {
            setStatusText("Password is empty");
            return;
        }

        Date currentDate = new Date();
        java.util.Date utilDate = currentDate;
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        user = new UserEntity(title.getText(), firstname.getText(), middelname.getText(), lastname.getText(), sqlDate, email.getText());
        user.setCompany(companyList.getSelectionModel().getSelectedItem());
        user.setRole(roleValue);
        user.setCreatedBy(CurrentUser.getInstance().getUserEntity());
        user.setCreatedByName(user.getCreatedBy().getFirstName());
        user.setCompanyName(user.getCompany().getName());

        Long userID = domainHandler.user().createUser(user, password.getText());

        if (userID != 0L) {
            user.setId(userID);
            closeDialog(event);

        } else {
            setStatusText("Something went wrong");
        }

    }

    @FXML
    public void cancel(ActionEvent event) {
        closeDialog(event);
    }

    private void setStatusText(String text) {
        statusText.setText(text);
        statusText.setVisible(true);
    }

    /**
     * closes the last opened stage
     */
    private void closeDialog(ActionEvent event) {
        //gets the node of the given event
        Node source = (Node) event.getSource();
        //gets that nodes stage
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
