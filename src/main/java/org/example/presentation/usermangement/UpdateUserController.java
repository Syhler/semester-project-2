package org.example.presentation.usermangement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.domain.Company;
import org.example.domain.DomainFacade;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.presentation.multipleLanguages.LanguageHandler;
import org.example.presentation.utilities.CurrentUser;
import org.example.presentation.utilities.UsermanagementUtilities;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class UpdateUserController implements Initializable {

    private DomainFacade domainHandler = new DomainFacade();
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
    private ComboBox<Company> companyList;
    @FXML
    private ComboBox<Role> roleList;

    @FXML
    private Label statusText;

    private ObservableList<Company> companyEntities = FXCollections.observableArrayList();

    private String titleName = "";
    private Role roleValue;

    private User userToUpdate = null;
    private User user = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        var cellFactory = UsermanagementUtilities.cellFactoryUserManagement();


        companyList.setCellFactory(cellFactory);
        companyList.setButtonCell(cellFactory.call(null));

        var companies = domainHandler.getAllCompanies();
        companyEntities.addAll(companies);

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
         * removes the select role option increase user doesn't have admin privilege
         */
        if (CurrentUser.getInstance().getUser().getRole() != Role.Admin) {
            roleList.setDisable(true);
        }
    }

    /**
     * Opens the CreateUser page and waits until the user is created or cancel
     *
     * @return UserEntity
     */
    public User openUpdateUser(ActionEvent event, User userToUpdate) {

        titleName = UsermanagementUtilities.setStageTitleCreateUpdateUser(userToUpdate.getRole());

        var updateUserStage = new Stage();

        try {
            FXMLLoader myLoader = App.getLoader("userManagementUpdate");
            updateUserStage.setScene(new Scene(myLoader.load()));
            UpdateUserController updateUserController = myLoader.getController();
            updateUserController.roleValue = userToUpdate.getRole();
            updateUserController.userToUpdate = userToUpdate;

            updateUserController.firstname.setText(userToUpdate.getName().getFirstName());
            updateUserController.middelname.setText(userToUpdate.getName().getFirstMiddleName());
            updateUserController.lastname.setText(userToUpdate.getName().getLastName());
            updateUserController.email.setText(userToUpdate.getEmail());

            var companies = domainHandler.getAllCompanies();
            companyEntities.addAll(companies);

            // Selecting the users current company in company Combobox
            for (Company company : companyEntities) {
                if (userToUpdate.getCompany().getId() == company.getId()) {
                    updateUserController.companyList.getSelectionModel().select(companyEntities.indexOf(company));
                    break;
                }
            }

            updateUserController.title.setText(userToUpdate.getTitle());
            updateUserController.roleList.setValue(userToUpdate.getRole());

            updateUserStage.setTitle(LanguageHandler.getText("updateUserTitle") +" "+ titleName);
            updateUserStage.initModality(Modality.WINDOW_MODAL);
            updateUserStage.initOwner(((Node) event.getTarget()).getScene().getWindow());
            updateUserStage.getIcons().add(new Image(App.class.getResourceAsStream("loginImages/tv2trans.png")));
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
        String validationMessage = UsermanagementUtilities.formValidation(firstname.getText(),lastname.getText(),email.getText(),companyList.getSelectionModel().getSelectedItem(),title.getText());
        if (validationMessage != null)
        {
            setStatusText(validationMessage);
            return;
        }

        Date currentDate = new Date();

        java.util.Date utilDate = currentDate;
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        user = new User(
                userToUpdate.getId(),
                title.getText(),
                firstname.getText(),
                middelname.getText(),
                lastname.getText(),
                sqlDate,
                email.getText(),
                null,
                userToUpdate.getCreatedBy(),
                companyList.getSelectionModel().getSelectedItem());


        if (CurrentUser.getInstance().getUser().getRole() != Role.Admin) {
            user.setRole(roleValue);
        } else {
            user.setRole(roleList.getValue());
        }

        boolean userWasUpdated = false;

        if (password.getText().isEmpty())
        {
            userWasUpdated = user.update();
        } else {
            userWasUpdated = user.update(password.getText());
        }



        if (userWasUpdated) {
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
