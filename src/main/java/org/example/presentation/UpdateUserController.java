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
import org.example.OLDdomain.DomainHandler;
import org.example.OLDentity.CompanyEntity;
import org.example.OLDentity.Role;
import org.example.OLDentity.UserEntity;
import org.example.presentation.multipleLanguages.LanguageHandler;
import org.example.presentation.utilities.UsermanagementUtilities;

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

    private ObservableList<CompanyEntity> companyEntities = FXCollections.observableArrayList();

    private String titleName = "";
    private Role roleValue;

    private UserEntity userToUpdate = null;
    private UserEntity user = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        var cellFactory = UsermanagementUtilities.cellFactoryUserManagement();


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

        /**
         * removes the select role option incase user doesn't have admin priviledge
         */
        if (CurrentUser.getInstance().getUserEntity().getRole() != Role.Admin) {
            roleList.setDisable(true);
        }
    }

    /**
     * Opens the CreateUser page and waits until the user is created or cancel
     *
     * @return UserEntity
     */
    public UserEntity openUpdateUser(ActionEvent event, UserEntity userToUpdate, Role role) {

        titleName = UsermanagementUtilities.setStageTitleCreateUpdateUser(role);

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
                if (userToUpdate.getCompany().getId() == company.getId()) {
                    updateUserController.companyList.getSelectionModel().select(companyEntities.indexOf(company));
                }
            }

            updateUserController.title.setText(userToUpdate.getTitle());
            updateUserController.roleList.setValue(userToUpdate.getRole());

            updateUserStage.setTitle(LanguageHandler.getText("updateUserTitle") +" "+ titleName);
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

    /**
     * Updates the chosen user according to input fields, closes dialog if succesfull
     * @param event
     * @throws IOException
     */
    @FXML
    public void updateUserFromInput(ActionEvent event) throws IOException {
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
        if (CurrentUser.getInstance().getUserEntity().getRole() != Role.Admin) {
            user.setRole(roleValue.getValue());
        } else {
            user.setRole(roleList.getValue());
        }

        user.setCompanyName(user.getCompany().getName());
        user.setId(userToUpdate.getId());

        boolean userWasCreated = domainHandler.user().updateUser(user, password.getText());

        if (userWasCreated) {
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
