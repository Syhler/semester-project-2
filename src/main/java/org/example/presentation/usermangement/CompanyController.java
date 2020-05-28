package org.example.presentation.usermangement;

import javafx.application.Platform;
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
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.domain.buisnessComponents.Company;
import org.example.domain.applicationFacade.DomainFacade;
import org.example.presentation.multipleLanguages.Language;
import org.example.presentation.multipleLanguages.LanguageHandler;
import org.example.presentation.utilities.UsermanagementUtilities;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CompanyController implements Initializable {
    private DomainFacade domainHandler = new DomainFacade();
    @FXML
    private Button createCompany;
    @FXML
    private Button updateCompany;
    @FXML
    private Button deleteCompany;
    @FXML
    private Button closeCompany;
    @FXML
    private Button reactivateCompany;

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
    private Label inactiveCompanyLabel;
    @FXML
    private Label activeCompanyLabel;

    @FXML
    private TextField companyNameInput;
    @FXML
    private TextField companyId;
    @FXML
    private TextField companyNameToUpdate;

    @FXML
    private ListView<Company> companyList;
    @FXML
    private ListView<Company> deletedCompaniesList;

    private final ObservableList<Company> companyEntities = FXCollections.observableArrayList();
    private final ObservableList<Company> deletedCompanyEntities = FXCollections.observableArrayList();


    @FXML
    private Label statusText;


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
        reactivateCompany.setText(LanguageHandler.getText("unDelete"));
        inactiveCompanyLabel.setText(LanguageHandler.getText("inactiveCompanies"));
        activeCompanyLabel.setText(LanguageHandler.getText("activeCompanies"));


        var cellFactory = UsermanagementUtilities.cellFactoryUserManagement();

        companyList.setCellFactory(cellFactory);
        deletedCompaniesList.setCellFactory(cellFactory);


        var thread = new Thread(loadAllCompanies());
        thread.start();
        var thread2 = new Thread(loadAllDeletedCompanies());
        thread2.start();

        companyList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Company>() {

            /**
             * Adds an eventlistener to the ListView cells, so data from clicked cell is displayed in update company inputs
             */
            @Override
            public void changed(ObservableValue<? extends Company> observable, Company oldValue, Company newValue) {
                companyNameToUpdate.setText(newValue.getName());
                companyId.setText(String.valueOf(newValue.getId()));
            }


        });

    }

    private Runnable loadAllCompanies()
    {
        return () ->
        {
            Platform.runLater(() -> setStatusText("Loading..."));
            var companies = domainHandler.getAllCompanies();
            companyEntities.addAll(companies);

            Platform.runLater(()->
            {
                companyList.setItems(companyEntities);
                setStatusText("");
            });
        };
    }

    private Runnable loadAllDeletedCompanies()
    {
        return () ->
        {
            Platform.runLater(() -> setStatusText("Loading..."));

            var deletedCompanies = domainHandler.getAllDeletedCompanies();
            if (deletedCompanies != null) {
                deletedCompanyEntities.addAll(deletedCompanies);
            }

            Platform.runLater(()->
            {
                deletedCompaniesList.setItems(deletedCompanyEntities);
                setStatusText("");
            });
        };
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
            companyStage.setTitle(LanguageHandler.getText("companyTitle"));
            companyStage.initModality(Modality.WINDOW_MODAL);
            companyStage.initOwner(((Node) event.getTarget()).getScene().getWindow());
            companyStage.getIcons().add(new Image(App.class.getResourceAsStream("loginImages/tv2trans.png")));
            companyStage.setResizable(false);
            companyStage.showAndWait();

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
    public void createCompany(ActionEvent event)
    {

        var thread = new Thread(() ->
        {
            Company newCompany = new Company(companyNameInput.getText());

            newCompany = domainHandler.createCompany(newCompany);

            Company finalNewCompany = newCompany;
            Platform.runLater(() ->
            {
                if (finalNewCompany.getId() != 0) {
                    setStatusText(finalNewCompany.getName() + " "+LanguageHandler.getText("companyCreated"));
                    companyEntities.add(finalNewCompany);

                } else {
                    setStatusText(finalNewCompany.getName() + " "+LanguageHandler.getText("companyNotCreated"));
                }
            });
        });

        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Updates selected company from the list with new name
     * @param event
     * @throws IOException
     * @Return boolean, true if company was updated succesfully
     */
    @FXML
    public void updateCompany(ActionEvent event) throws IOException
    {
        var thread = new Thread(() ->
        {
            Company updatedCompany = new Company(companyNameToUpdate.getText());
            updatedCompany.setId(Long.parseLong(companyId.getText()));

            boolean companyWasUpdated = updatedCompany.update();

            Platform.runLater(() ->
            {
                if (companyWasUpdated) {
                    setStatusText(updatedCompany.getName() + " "+LanguageHandler.getText("companyUpdated"));
                    companyEntities.remove(companyList.getSelectionModel().getSelectedItem());
                    companyEntities.add(updatedCompany);
                } else {
                    setStatusText(updatedCompany.getName() + " "+LanguageHandler.getText("companyNotUpdated"));
                }
            });
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Deletes selected company from list
     * @param event
     * @throws IOException
     * @Return boolean, true if company was deleted
     */
    @FXML
    public void deleteCompany(ActionEvent event) throws IOException
    {
        Company selectedCompany = companyList.getSelectionModel().getSelectedItem();

        boolean companyWasDeleted = selectedCompany.delete();

        if (companyWasDeleted) {
            setStatusText(selectedCompany.getName() + " "+LanguageHandler.getText("companyDeleted"));
            companyEntities.remove(selectedCompany);
        } else {
            setStatusText(selectedCompany.getName() + " "+LanguageHandler.getText("companyNotDeleted"));
        }

        var thread = new Thread(() ->
        {
            Platform.runLater(() ->
            {
                if (selectedCompany.getId() != 0) {
                    deletedCompanyEntities.add(selectedCompany);

                }
            });
        });
        thread.start();
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

    @FXML
    private void unDeleteCompany(ActionEvent event)
    {
        Company selectedCompany = deletedCompaniesList.getSelectionModel().getSelectedItem();

        boolean companyWasRemoved = selectedCompany.unDeleteCompany();

        if (companyWasRemoved)
        {
            deletedCompanyEntities.remove(selectedCompany);
            setStatusText(selectedCompany.getName()+LanguageHandler.getText("companyWasReactivated"));
        } else {
            setStatusText(selectedCompany.getName()+LanguageHandler.getText("companyWasNotReactivated"));
        }

        var thread = new Thread(() ->
        {
            Platform.runLater(() ->
            {
                if (selectedCompany.getId() != 0) {
                    companyEntities.add(selectedCompany);

                }
            });
        });
        thread.start();
    }

}
