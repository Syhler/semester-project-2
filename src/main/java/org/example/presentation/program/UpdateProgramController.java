package org.example.presentation.program;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.App;
import org.example.OLDdomain.DomainHandler;
import org.example.OLDentity.*;
import org.example.presentation.utilities.ControllerUtility;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateProgramController implements Initializable {

    @FXML
    private Button cancelBtn;
    @FXML
    private Label updateProgramTitle;
    @FXML
    private TextArea updateInsertTitle;
    @FXML
    private Label updateProgramDescription;
    @FXML
    private TextArea updateInsertDescription;
    @FXML
    private Label updateProgramCompany;
    @FXML
    private Label updateProgramCredits;
    @FXML
    private Button updateCreditBtn;
    @FXML
    private Button updateProgramBtn;
    @FXML
    private Label remainingCharactersDesc;
    @FXML
    private Label remainingCharactersTitle;
    @FXML
    private Label updateProgramProducer;
    @FXML
    private ComboBox<CompanyEntity> chooseCompany;
    @FXML
    private ComboBox<UserEntity> chooseProducer;
    @FXML
    private TextArea creditList;
    @FXML
    private TextArea producerList;
    @FXML
    private Button addSelectedProducer;
    @FXML
    private Label addCreditHeader;
    @FXML
    private ComboBox<CreditEntity> chooseCredit;
    @FXML
    private Button addCreditButton;

    private int maxSizeTitle = 100;
    private int maxSizeDesc = 1000;
    private List<UserEntity> producers = new ArrayList<UserEntity>();
    private List<CreditEntity> credits = new ArrayList<CreditEntity>();
    private CompanyEntity company;
    private ProgramEntity programEntity;

    private DomainHandler domainHandler = new DomainHandler();
    private long programId;


    @FXML
    private void closeUpdateProgram(ActionEvent event)
    {
        ControllerUtility.closeProgram(event);
    }

    /**
     * Opens "updateProgram.fxml" as a popup scene.
     * @param programEntity of the program that you want to open
     * @return a programEntity with its different variables filled.
     */
    public ProgramEntity openView(ProgramEntity programEntity) throws IOException {

        FXMLLoader loader = null;
        loader = App.getLoader("updateProgram");
        Parent node = loader.load();
        UpdateProgramController updateProgramController = loader.<UpdateProgramController>getController();


        if (programEntity != null) {
            updateProgramController.updateInsertTitle.setText(programEntity.getName());
            updateProgramController.updateInsertDescription.setText(programEntity.getDescription());


            if (programEntity.getProducer() != null) {
                for (int i = 0; i < programEntity.getProducer().size(); i++) {
                    updateProgramController.producerList.appendText(programEntity.getProducer().get(i).getName() + "\n");
                }
            }

            if (programEntity.getCredits() != null) {
                for (int i = 0; i < programEntity.getCredits().size(); i++) {
                    updateProgramController.creditList.appendText(programEntity.getCredits().get(i).getActor().getNameAndTitle() + "\n");
                }
            }

            updateProgramController.programId = programEntity.getId();
        }




        Scene scene = new Scene(node);

        Stage stage = new Stage();
        stage.setTitle(LanguageHandler.getText("updateProgramStageTitle"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();

        return updateProgramController.programEntity;
    }

    /**
     * Calculates, updates and inserts how many characters the user has remaining in the description textArea
     */
    @FXML
    private void remainingCharactersDesc()
    {
        updateInsertDescription.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                remainingCharactersDesc.setText(ControllerUtility.remainingCharacters(updateInsertDescription, maxSizeDesc));
            }
        });
    }

    /**
     * Calculates, updates and inserts how many characters the user has remaining in the title textArea
     */
    @FXML
    private void remainingCharactersTitle()
    {
        updateInsertTitle.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                remainingCharactersTitle.setText(ControllerUtility.remainingCharacters(updateInsertTitle, maxSizeTitle));
            }
        });
    }

    /**
     * Gets all companies from the database and puts them in a list
     * @return list of CompanyEntity
     */
    public List<CompanyEntity> companiesFromDatabase()
    {
        List<CompanyEntity> companies = new ArrayList<>();
        companies = domainHandler.company().getCompanies();

        return companies;
    }

    /**
     * Enables user to choose a company from a comboBox
     */
    public void chooseCompany()
    {
        chooseCompany.getItems().addAll(companiesFromDatabase());

        Callback<ListView<CompanyEntity>, ListCell<CompanyEntity>> cellFactory = new Callback<>() {

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
        chooseCompany.setCellFactory(cellFactory);
        chooseCompany.setButtonCell(cellFactory.call(null));
    }


    /**
     * Enables user to choose a producer from a comboBox
     */
    public void chooseProducer()
    {
        var producers = domainHandler.user().getUserByRole(Role.Producer);

        chooseProducer.getItems().addAll(producers);

        Callback<ListView<UserEntity>, ListCell<UserEntity>> cellFactory = new Callback<>() {

            @Override
            public ListCell<UserEntity> call(ListView<UserEntity> l) {
                return new ListCell<UserEntity>() {

                    @Override
                    protected void updateItem(UserEntity item, boolean empty) {
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

        chooseProducer.setCellFactory(cellFactory);
        chooseProducer.setButtonCell(cellFactory.call(null));
    }

    /**
     * Adds the selected producer from the combobox, to a list and a text area
     * @param event
     */
    public void addProducer(ActionEvent event)
    {
        UserEntity producer = chooseProducer.getSelectionModel().getSelectedItem();
        producerList.appendText(producer.getName()+"\n");
        producers.add(chooseProducer.getSelectionModel().getSelectedItem());
    }

    /**
     * Test method, should be deleted. Creates some CreditEntity and put them in a list
     * @return list of CreditEntity
     */
    public List<CreditEntity> creditsFromDatabase()
    {
        List<CreditEntity> credits = new ArrayList<CreditEntity>();
        var actors = domainHandler.user().getUserByRole(Role.Actor);
        for (UserEntity actor: actors) {
            credits.add(new CreditEntity(actor));
        }

        return credits;
    }

    /**
     * Enables user to choose a credit from a comboBox
     */
    public void chooseCredit()
    {
        chooseCredit.getItems().addAll(creditsFromDatabase());

        Callback<ListView<CreditEntity>, ListCell<CreditEntity>> cellFactory = new Callback<>() {

            @Override
            public ListCell<CreditEntity> call(ListView<CreditEntity> l) {
                return new ListCell<CreditEntity>() {

                    @Override
                    protected void updateItem(CreditEntity item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getActor().getNameAndTitle());
                        }
                    }
                };
            }
        };
        chooseCredit.setCellFactory(cellFactory);
        chooseCredit.setButtonCell(cellFactory.call(null));
    }

    /**
     * Adds the selected credit from the combobox, to a list and a text area
     * @param event
     */
    public void addCredit(ActionEvent event)
    {
        CreditEntity credit = chooseCredit.getSelectionModel().getSelectedItem();
        creditList.appendText(credit.getActor().getNameAndTitle()+"\n");
        credits.add(chooseCredit.getSelectionModel().getSelectedItem());
    }

    /**
     * On the click of a button, opens a scene where a credit can be made and adds the made credit to a list and text area
     * @param event
     * @throws IOException
     */
    @FXML
    public void goToCreateCredit(ActionEvent event) throws IOException {
        CreditController creditController = new CreditController();
        UserEntity credit = creditController.openView();

        if (credit == null) return;

        creditList.appendText(credit.getNameAndTitle() +"\n");
        CreditEntity creditEntity = new CreditEntity(0,credit);
        credits.add(creditEntity);
    }

    public String getTitle()
    {
        return updateInsertTitle.getText();
    }

    public String getDescription()
    {
        return updateInsertDescription.getText();
    }

    /**
     * Updates the different objects in a program
     * @param event
     */
    @FXML
    public void updateProgram(ActionEvent event)
    {
        company = chooseCompany.getSelectionModel().getSelectedItem();

        String title = getTitle();

        String description = getDescription();;

        programEntity = new ProgramEntity(programId, title, description, company, producers, credits);

        closeUpdateProgram(event);
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        creditList.setEditable(false);
        producerList.setEditable(false);

        remainingCharactersDesc();
        remainingCharactersDesc.setText(ControllerUtility.remainingCharacters(updateInsertDescription, maxSizeDesc));
        ControllerUtility.maxTextSize(updateInsertDescription, maxSizeDesc);

        remainingCharactersTitle();
        remainingCharactersTitle.setText(ControllerUtility.remainingCharacters(updateInsertTitle, maxSizeTitle));
        ControllerUtility.maxTextSize(updateInsertTitle, maxSizeTitle);

        chooseCompany();
        chooseCompany.getSelectionModel().selectFirst();
        chooseProducer();
        chooseProducer.getSelectionModel().selectFirst();
        chooseCredit();
        chooseCredit.getSelectionModel().selectFirst();

        addSelectedProducer.setText(LanguageHandler.getText("add"));
        updateProgramBtn.setText(LanguageHandler.getText("updateProgram"));
        cancelBtn.setText(LanguageHandler.getText("cancel"));
        updateProgramTitle.setText(LanguageHandler.getText("titleHeader"));
        updateInsertTitle.setPromptText(LanguageHandler.getText("insertTitle"));
        updateProgramDescription.setText(LanguageHandler.getText("descriptionHeader"));
        updateInsertDescription.setPromptText(LanguageHandler.getText("insertDescription"));
        updateProgramCompany.setText(LanguageHandler.getText("programCompany"));
        updateProgramCredits.setText(LanguageHandler.getText("programCredits"));
        updateCreditBtn.setText(LanguageHandler.getText("createCreditStageTitle"));
        updateProgramProducer.setText(LanguageHandler.getText("producer"));
        addCreditButton.setText(LanguageHandler.getText("add"));
        addCreditHeader.setText(LanguageHandler.getText("addCreditHeader"));

    }
}
