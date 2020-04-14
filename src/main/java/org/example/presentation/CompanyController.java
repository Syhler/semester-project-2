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
    private TextField companyNameInput;
    @FXML
    private TextField companyId;
    @FXML
    private TextField companyNameToUpdate;

    @FXML
    private ListView<CompanyEntity> companyList;

    @FXML
    private Label statusText;

    public ObservableList<CompanyEntity> companyEntities = FXCollections.observableArrayList();

    private CompanyEntity company = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Callback<ListView<CompanyEntity>, ListCell<CompanyEntity>> cellFactoryComp = new Callback<ListView<CompanyEntity>, ListCell<CompanyEntity>>() {

            @Override
            public ListCell<CompanyEntity> call(ListView<CompanyEntity> l) {
                return new ListCell<CompanyEntity>() {

                    @Override
                    protected void updateItem(CompanyEntity item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setDisable(false);
                            setGraphic(null);
                            setText("");
                        } else {
                            setText(item.getName());
                        }
                    }
                } ;
            }
        };



        companyList.setCellFactory(cellFactoryComp);
        companyEntities.addAll(domainHandler.company().getCompanies());

        companyList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CompanyEntity>() {

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
     * @return currentUser
     */
    public void openCompanyController(ActionEvent event)
    {
        var companyStage = new Stage();

        try {
            FXMLLoader myLoader = App.getLoader("company");
            companyStage.setScene(new Scene(myLoader.load()));
            CompanyController createusercontrol = myLoader.getController();

            companyStage.setTitle("Companies");
            companyStage.initModality(Modality.WINDOW_MODAL);
            companyStage.initOwner(((Node)event.getTarget()).getScene().getWindow());
            companyStage.setResizable(false);
            companyStage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    public void createCompany(ActionEvent event) throws IOException
    {
        CompanyEntity newCompany = new CompanyEntity(companyNameInput.getText());

        Long id = domainHandler.company().createCompany(newCompany);

        if (id != null)
        {
            newCompany.setId(id);
            setStatusText(newCompany.getName()+" was created");
            companyEntities.add(newCompany);

        } else
        {
            setStatusText(newCompany.getName()+" wasn't created");
        }



    }
    @FXML
    public void updateCompany(ActionEvent event) throws IOException
    {
        CompanyEntity updatedCompany = new CompanyEntity(companyNameToUpdate.getText());
        updatedCompany.setId(Long.parseLong(companyId.getText()));

        if (domainHandler.company().updateCompany(updatedCompany))
        {
            setStatusText(updatedCompany.getName()+" was updated");
            companyEntities.remove(companyList.getSelectionModel().getSelectedItem());
            companyEntities.add(updatedCompany);
        } else
            {
                setStatusText(updatedCompany.getName()+" wasn't updated");
            }
    }

    @FXML
    public void deleteCompany(ActionEvent event) throws IOException
    {
        CompanyEntity selectedCompany = companyList.getSelectionModel().getSelectedItem();

        if (domainHandler.company().deleteCompany(selectedCompany))
        {
            setStatusText(selectedCompany.getName()+" deleted");
            companyEntities.remove(selectedCompany);
        } else
            {
                setStatusText(selectedCompany.getName()+" wasn't deleted");
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
