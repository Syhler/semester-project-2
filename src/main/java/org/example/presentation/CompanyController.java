package org.example.presentation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import org.example.presentation.multipleLanguages.LanguageHandler;
import org.example.presentation.utilities.UsermanagementUtilities;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class CompanyController implements Initializable {

    private DomainHandler domainHandler = new DomainHandler();
    @FXML
    private Button createCompany;
    @FXML
    private Button updateCompany;
    @FXML
    private Button deleteCompany;
    @FXML
    private Button closeCompany;

    @FXML
    private Label createCompanyLabel;
    @FXML
    private Label companyNameLabel;
    @FXML
    private Label updateCompanyLabel;
    @FXML
    private Label companyIdLabel;
    @FXML
    private Label updateCompanyLabelName;


    @FXML
    private TextField companyNameInput;
    @FXML
    private TextField companyId;
    @FXML
    private TextField companyNameToUpdate;

    @FXML
    private ListView<CompanyEntity> companyList;

    @FXML
    private Label statusText;

    private ObservableList<CompanyEntity> companyEntities = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * Language
         */
        createCompanyLabel.setText(LanguageHandler.getText("createCompanyLabel"));
        companyNameLabel.setText(LanguageHandler.getText("companyNameLabel"));
        createCompany.setText(LanguageHandler.getText("createCompany"));
        updateCompanyLabel.setText(LanguageHandler.getText("updateCompanyLabel"));
        companyIdLabel.setText(LanguageHandler.getText("companyIdLabel"));
        updateCompanyLabelName.setText(LanguageHandler.getText("updateCompanyLabelName"));
        updateCompany.setText(LanguageHandler.getText("updateCompany"));
        deleteCompany.setText(LanguageHandler.getText("deleteCompany"));
        closeCompany.setText(LanguageHandler.getText("closeCompany"));


        /**
         * Call to UsermangementUtilities to get cellfactory
         *
         * @return Callback for cellfactory
         */
        var cellFactory = UsermanagementUtilities.cellFactoryUsermanagemnt();

        companyList.setCellFactory(cellFactory);
        companyEntities.addAll(domainHandler.company().getCompanies());

        companyList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CompanyEntity>() {

            /**
             * Adds an eventlistener to the ListView cells, so data from clicked cell is displayed in update company inputs
             * @param observable
             * @param oldValue
             * @param newValue
             */
            @Override
            public void changed(ObservableValue<? extends CompanyEntity> observable, CompanyEntity oldValue, CompanyEntity newValue) {
                companyNameToUpdate.setText(newValue.getName());
                companyId.setText(String.valueOf(newValue.getId()));
            }


        });

        companyList.setItems(companyEntities);
    }


    /**
     * Opens the CreateUser page and waits until the user is created or cancel
     *
     * @return currentUser
     */
    public void openCompanyController(ActionEvent event) {
        var companyStage = new Stage();

        try {
            FXMLLoader myLoader = App.getLoader("company");
            companyStage.setScene(new Scene(myLoader.load()));
            CompanyController createusercontrol = myLoader.getController();

            companyStage.setTitle(LanguageHandler.getText("companyTitle"));
            companyStage.initModality(Modality.WINDOW_MODAL);
            companyStage.initOwner(((Node) event.getTarget()).getScene().getWindow());
            companyStage.setResizable(false);
            companyStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Creates company from input fields and returns the id from database
     * @param event
     * @throws IOException
     * @Return long ID from database
     */
    @FXML
    public void createCompany(ActionEvent event) throws IOException {
        CompanyEntity newCompany = new CompanyEntity(companyNameInput.getText());

        long id = domainHandler.company().createCompany(newCompany);

        if (id != 0) {
            newCompany.setId(id);
            setStatusText(newCompany.getName() + " "+LanguageHandler.getText("companyCreated"));
            companyEntities.add(newCompany);

        } else {
            setStatusText(newCompany.getName() + " "+LanguageHandler.getText("companyNotCreated"));
        }


    }

    /**
     * Updates selected company from the list with new name
     * @param event
     * @throws IOException
     * @Return boolean, true if company was updated succesfully
     */
    @FXML
    public void updateCompany(ActionEvent event) throws IOException {
        CompanyEntity updatedCompany = new CompanyEntity(companyNameToUpdate.getText());
        updatedCompany.setId(Long.parseLong(companyId.getText()));

        boolean companyWasUpdated = domainHandler.company().updateCompany(updatedCompany);

        if (companyWasUpdated) {
            setStatusText(updatedCompany.getName() + " "+LanguageHandler.getText("companyUpdated"));
            companyEntities.remove(companyList.getSelectionModel().getSelectedItem());
            companyEntities.add(updatedCompany);
        } else {
            setStatusText(updatedCompany.getName() + " "+LanguageHandler.getText("companyNotUpdated"));
        }
    }

    /**
     * Deletes selected company from list
     * @param event
     * @throws IOException
     * @Return boolean, true if company was deleted
     */
    @FXML
    public void deleteCompany(ActionEvent event) throws IOException {
        CompanyEntity selectedCompany = companyList.getSelectionModel().getSelectedItem();

        boolean companyWasDeleted = domainHandler.company().deleteCompany(selectedCompany);

        if (companyWasDeleted) {
            setStatusText(selectedCompany.getName() + " "+LanguageHandler.getText("companyDeleted"));
            companyEntities.remove(selectedCompany);
        } else {
            setStatusText(selectedCompany.getName() + " "+LanguageHandler.getText("companyNotDeleted"));
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
