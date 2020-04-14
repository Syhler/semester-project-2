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
import org.example.presentation.multipleLanguages.Language;
import org.example.presentation.multipleLanguages.LanguageHandler;

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
    private Label firstNameCreate;
    @FXML
    private Label middleNameCreate;
    @FXML
    private Label lastNameCreate;
    @FXML
    private Label emailCreate;
    @FXML
    private Label companyCreate;
    @FXML
    private Label titleCreate;
    @FXML
    private Label passwordCreate;
    @FXML
    private Button createUserFromInput;
    @FXML
    private Button btnCancel;


    @FXML
    private ComboBox<CompanyEntity> companyList;

    @FXML
    private Label statusText;

    private ObservableList<CompanyEntity> companyEntities = FXCollections.observableArrayList();

    private String rolename = "";
    private Role roleValue;

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

        firstNameCreate.setText(LanguageHandler.getText("firstName"));
        middleNameCreate.setText(LanguageHandler.getText("middleName"));
        lastNameCreate.setText(LanguageHandler.getText("lastName"));
        emailCreate.setText(LanguageHandler.getText("email"));
        titleCreate.setText(LanguageHandler.getText("title"));
        companyCreate.setText(LanguageHandler.getText("company"));
        passwordCreate.setText(LanguageHandler.getText("password"));
        createUserFromInput.setText(LanguageHandler.getText("createBtn"));
        btnCancel.setText(LanguageHandler.getText("cancelBtn"));
    }


    /**
     * Opens the CreateUser page and waits until the user is created or cancel
     *
     * @return currentUser
     */
    public UserEntity openCreateUser(ActionEvent event, Role role) {
        switch (role) {
            case Admin:
                rolename = LanguageHandler.getText("admin");
                break;
            case Manufacture:
                rolename = LanguageHandler.getText("manufacture");
                break;
            case Producer:
                rolename = LanguageHandler.getText("producer");
                break;
            case Actor:
                rolename = LanguageHandler.getText("actor");
                break;
        }

        var createUserStage = new Stage();

        try {
            FXMLLoader myLoader = App.getLoader("userManagementCreate");
            createUserStage.setScene(new Scene(myLoader.load()));
            CreateUserController createusercontrol = myLoader.getController();
            createusercontrol.roleValue = role;

            createUserStage.setTitle(LanguageHandler.getText("createUserTitle") +" "+ rolename);
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
    public void createUserFromInput(ActionEvent event) throws Exception {
        if (firstname.getText().isEmpty()) {
            setStatusText(LanguageHandler.getText("firstnameEmpty"));
            return;
        } else if (lastname.getText().isEmpty()) {
            setStatusText(LanguageHandler.getText("lastnameEmpty"));
            return;
        } else if (email.getText().isEmpty()) {
            setStatusText(LanguageHandler.getText("emailEmpty"));
            return;
        } else if (companyList.getSelectionModel().isEmpty()) {
            setStatusText(LanguageHandler.getText("companyEmpty"));
            return;
        } else if (title.getText().isEmpty()) {
            setStatusText(LanguageHandler.getText("titleEmpty"));
            return;
        } else if (password.getText().isEmpty()) {
            setStatusText(LanguageHandler.getText("passwordEmpty"));
            return;
        }

        Date currentDate = new Date();
        java.util.Date utilDate = currentDate;
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        user = new UserEntity(title.getText(), firstname.getText(), middelname.getText(), lastname.getText(), sqlDate, email.getText());
        user.setCompany(companyList.getSelectionModel().getSelectedItem());
        user.setRole(roleValue.getValue());
        user.setCreatedBy(CurrentUser.getInstance().getUserEntity());
        user.setCreatedByName(user.getCreatedBy().getFirstName());
        user.setCompanyName(user.getCompany().getName());

        long userID = domainHandler.user().createUser(user, password.getText());

        if (userID != 0) {
            user.setId(userID);
            closeDialog(event);

        } else {
            setStatusText(LanguageHandler.getText("somethingWrong"));
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
