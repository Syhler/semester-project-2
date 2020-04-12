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
    private TextField id;
    @FXML
    private TextField password;
    @FXML
    private Button createUserFromInput;


    @FXML
    private ComboBox<CompanyEntity> companyList;

    @FXML
    private Label statusText;

    public ObservableList<CompanyEntity> companyEntities = FXCollections.observableArrayList();

    private String rolename = "";
    private int roleValue;

    private UserEntity user = null;

    private boolean updated = false;



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
                } ;
            }
        };

        companyList.setCellFactory(cellFactory);
        companyList.setButtonCell(cellFactory.call(null));
        companyEntities.addAll(domainHandler.company().getCompanies());
        companyList.setItems(companyEntities);





    }


    /**
     * Opens the CreateUser page and waits until the user is created or cancel
     * @return currentUser
     */
    public UserEntity openUpdateUser(ActionEvent event,UserEntity userToUpdate, int role)
    {
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
                } ;
            }
        };
        switch (role)
        {
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
            updateUserController.user = userToUpdate;

            updateUserController.firstname.setText(userToUpdate.getFirstName());
            updateUserController.middelname.setText(userToUpdate.getMiddleName());
            updateUserController.lastname.setText(userToUpdate.getLastName());
            updateUserController.email.setText(userToUpdate.getEmail());
            updateUserController.companyList.setValue(userToUpdate.getCompany());
            updateUserController.title.setText(userToUpdate.getTitle());
            updateUserController.id.setText(Long.toString(userToUpdate.getId()));


            updateUserStage.setTitle("Update "+rolename);
            updateUserStage.initModality(Modality.WINDOW_MODAL);
            updateUserStage.initOwner(((Node)event.getTarget()).getScene().getWindow());
            updateUserStage.setResizable(false);
            updateUserStage.showAndWait();

            return updateUserController.user;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    @FXML
    public void updateUserFromInput(ActionEvent event) throws IOException
    {
        if (firstname.getText().isEmpty())
        {
            setStatusText("Firstname is empty");
            return;
        }
        else if (lastname.getText().isEmpty())
        {
            setStatusText("Lastname is empty");
            return;
        }
        else if (email.getText().isEmpty())
        {
            setStatusText("Email is empty");
            return;
        }
        else if (companyList.getSelectionModel().isEmpty())
        {
            setStatusText("A company must be selected");
            return;
        }
        else if (title.getText().isEmpty())
        {
            setStatusText("Title is empty");
            return;
        }
        else if (password.getText().isEmpty())
        {
            setStatusText("Password is empty");
            return;
        }

        Date currentDate = new Date();

        user = new UserEntity(title.getText(),firstname.getText(),middelname.getText(),lastname.getText(),currentDate,email.getText());
        user.setCompany(companyList.getSelectionModel().getSelectedItem());
        user.setRole(roleValue);
        user.setCreatedBy(CurrentUser.getInstance().getUserEntity());
        user.setCreatedByName(user.getCreatedBy().getFirstName());
        user.setCompanyName(user.getCompany().getName());
        user.setId(Long.parseLong(id.getText()));


        if (domainHandler.user().updateUser(user, password.getText()))
        {
            closeDialog(event);

        } else
            {
            setStatusText("Something went wrong");
            }

    }

    @FXML
    public void cancel(ActionEvent event)
    {
        closeDialog(event);
    }

    private void setStatusText(String text)
    {
        statusText.setText(text);
        statusText.setVisible(true);
    }

    /**
     * closes the last opened stage
     */
    private void closeDialog(ActionEvent event)
    {
        //gets the node of the given event
        Node source = (Node)  event.getSource();
        //gets that nodes stage
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
