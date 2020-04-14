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
import org.example.entity.Role;
import org.example.entity.UserEntity;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class UpdateUserController implements Initializable {

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
    private Label firstNameUpdate;
    @FXML
    private Label middleNameUpdate;
    @FXML
    private Label lastNameUpdate;
    @FXML
    private Label emailUpdate;
    @FXML
    private Label companyUpdate;
    @FXML
    private Label titleUpdate;
    @FXML
    private Label passwordUpdate;
    @FXML
    private Button createUserFromInput;
    @FXML
    private Button btnCancel;

    @FXML
    private ComboBox<CompanyEntity> companyList;
    @FXML
    private ComboBox<Role> roleList;

    @FXML
    private Label statusText;

    public ObservableList<CompanyEntity> companyEntities = FXCollections.observableArrayList();

    private String rolename = "";
    private int roleValue;

    private UserEntity userToUpdate = null;
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

        firstNameUpdate.setText(LanguageHandler.getText("firstName"));
        middleNameUpdate.setText(LanguageHandler.getText("middleName"));
        lastNameUpdate.setText(LanguageHandler.getText("lastName"));
        emailUpdate.setText(LanguageHandler.getText("email"));
        titleUpdate.setText(LanguageHandler.getText("title"));
        companyUpdate.setText(LanguageHandler.getText("company"));
        passwordUpdate.setText(LanguageHandler.getText("password"));
        createUserFromInput.setText(LanguageHandler.getText("updateBtn"));
        btnCancel.setText(LanguageHandler.getText("cancelBtn"));

        roleList.getItems().addAll(Role.Admin, Role.Manufacture, Role.Producer, Role.Actor);

        if (CurrentUser.getInstance().getUserEntity().getRole() != Role.Admin) {
            roleList.setDisable(true);
        }
    }

    /**
     * Opens the CreateUser page and waits until the user is created or cancel
     *
     * @return UserEntity
     */
    public UserEntity openUpdateUser(ActionEvent event, UserEntity userToUpdate, int role) {

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

        var updateUserStage = new Stage();

        try {
            FXMLLoader myLoader = App.getLoader("userManagementUpdate");
            updateUserStage.setScene(new Scene(myLoader.load()));
            UpdateUserController updateUserController = myLoader.getController();
            updateUserController.roleValue = role;
            updateUserController.userToUpdate = userToUpdate;

            updateUserController.firstname.setText(userToUpdate.getFirstName());
            updateUserController.middelname.setText(userToUpdate.getMiddleName());
            updateUserController.lastname.setText(userToUpdate.getLastName());
            updateUserController.email.setText(userToUpdate.getEmail());
            companyEntities.addAll(domainHandler.company().getCompanies());

            // Selecting the users current company in company Combobox

            for (CompanyEntity company : companyEntities) {
                if (userToUpdate.getCompany().getId().equals(company.getId())) {
                    updateUserController.companyList.getSelectionModel().select(companyEntities.indexOf(company));
                }
            }

            updateUserController.title.setText(userToUpdate.getTitle());
            updateUserController.roleList.setValue(userToUpdate.getRole());

            updateUserStage.setTitle("Update " + rolename);
            updateUserStage.initModality(Modality.WINDOW_MODAL);
            updateUserStage.initOwner(((Node) event.getTarget()).getScene().getWindow());
            updateUserStage.setResizable(false);
            updateUserStage.showAndWait();

            return updateUserController.user;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    public void updateUserFromInput(ActionEvent event) throws IOException {
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
        if (CurrentUser.getInstance().getUserEntity().getRole() != Role.Admin) {
            user.setRole(roleValue);
        } else {
            user.setRole(roleList.getValue());
        }

        //user.setCreatedBy(CurrentUser.getInstance().getUserEntity());
        //user.setCreatedByName(user.getCreatedBy().getFirstName());
        user.setCompanyName(user.getCompany().getName());
        user.setId(userToUpdate.getId());

        if (domainHandler.user().updateUser(user, password.getText())) {
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
