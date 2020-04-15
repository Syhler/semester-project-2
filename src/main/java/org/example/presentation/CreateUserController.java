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
import org.example.App;
import org.example.domain.DomainHandler;
import org.example.entity.CompanyEntity;
import org.example.entity.Role;
import org.example.entity.UserEntity;
import org.example.presentation.multipleLanguages.LanguageHandler;
import org.example.presentation.utilities.*;

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

    private String titleName = "";
    private Role roleValue;

    private UserEntity user = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * Call to UsermangementUtilities to get cellfactory
         *
         * @return Callback for cellfactory
         */
        var cellFactory = UsermanagementUtilities.cellFactoryUsermanagemnt();

        // setting cellFactory to companylist adding items from OobervableList to Combobox

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

        titleName = UsermanagementUtilities.setStageTitleCreateUpdateUser(role);

        var createUserStage = new Stage();

        try {
            FXMLLoader myLoader = App.getLoader("userManagementCreate");
            createUserStage.setScene(new Scene(myLoader.load()));
            CreateUserController createusercontrol = myLoader.getController();
            createusercontrol.roleValue = role;

            createUserStage.setTitle(LanguageHandler.getText("createUserTitle") +" "+ titleName);
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

    /**
     * Creates a userEntity from the input fields closes createuserController if create was succesfull
     * @param event
     * @throws Exception
     */
    @FXML
    public void createUserFromInput(ActionEvent event) throws Exception {

        String validationMessage = UsermanagementUtilities.formValidation(firstname.getText(),lastname.getText(),email.getText(),companyList.getSelectionModel().getSelectedItem(),title.getText(),password.getText());
        if (validationMessage != "")
        {
            setStatusText(validationMessage);
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

    /**
     * Closes stage depending on event argument
     * @param event
     */
    @FXML
    public void cancel(ActionEvent event) {
        closeDialog(event);
    }

    /**
     * Sets feedback text if inputs etc are wrong or missing
     * @param text
     */
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
