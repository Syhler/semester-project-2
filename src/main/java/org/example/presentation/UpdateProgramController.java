package org.example.presentation;

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
import org.example.domain.Credit;
import org.example.domain.DomainHandler;
import org.example.entity.*;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateProgramController implements Initializable {

    public Button cancelBtn;
    public Label updateProgTitle;
    public TextArea updateInsertTitle;
    public Label updateProgDescription;
    public TextArea updateInsertDescription;
    public Label updateProgCompany;
    public Label updateProgCredits;
    public Button updateCreditBtn;
    public Button updateProgramBtn;
    public Label remainingCharactersDesc;
    public int maxSizeDesc = 500;
    public Label remainingCharactersTitle;
    public int maxSizeTitle = 100;
    public Label updateProgProducer;
    public ComboBox<CompanyEntity> chooseCompany;
    public ComboBox<UserEntity> chooseProducer;
    public TextArea creditList;
    public TextArea producerList;
    public Button addSelectedProducer;
    public List<UserEntity> producers = new ArrayList<UserEntity>();
    public List<CreditEntity> credits = new ArrayList<CreditEntity>();
    public CompanyEntity company;
    public ProgramEntity programEntity;
    public Label addCreditHeader;
    public ComboBox<CreditEntity> chooseCredit;
    public Button addCreditButton;
    private DomainHandler domainHandler = new DomainHandler();
    private long programId;


    @FXML
    private void closeUpdateProgram(ActionEvent event)
    {
        ControllerUtility.closeProgram(event);
    }

    /**
     * Opens "updateProgram.fxml" as a popup scene.
     * @param programEntity
     * @return a programEntity with a title, description and Id.
     * @throws IOException
     */
    public ProgramEntity openView(ProgramEntity programEntity) throws IOException {

        FXMLLoader loader = null;
        loader = App.getLoader("updateProgram");
        Parent node = loader.load();
        UpdateProgramController updateProgramController = loader.<UpdateProgramController>getController();

        updateProgramController.updateInsertTitle.setText(programEntity.getName());
        updateProgramController.updateInsertDescription.setText(programEntity.getDescription());
        programId = programEntity.getId();

        Scene scene = new Scene(node);

        Stage stage = new Stage();
        stage.setTitle(LanguageHandler.getText("updateProgramStageTitle"));
        stage.setScene(scene);
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
     * Test method, should be deleted. Creates some companies and put them in a list.
     * @return list of CompanyEntity
     */
    public List<CompanyEntity> companyTest()
    {
        CompanyEntity companyTV2 = new CompanyEntity("TV2");
        CompanyEntity companyDR1 = new CompanyEntity("DR");
        CompanyEntity companyBlue = new CompanyEntity("BLU");
        List<CompanyEntity> companies = new ArrayList<>();
        companies.add(companyBlue);
        companies.add(companyTV2);
        companies.add(companyDR1);
        return companies;
    }

    /**
     * Enables user to choose a company from a comboBox
     */
    public void chooseCompany()
    {
        chooseCompany.getItems().addAll(companyTest());

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
     * Test method, should be deleted. Creates some producers and put them in a list.
     * @return list of UserEntity
     */
    public List<UserEntity> producerCreatorTest()
    {
        UserEntity userEntity1 = new UserEntity("Producer", "Hans", "Jørgen", "Producermand1", new Date(), "Hans@email.com");
        UserEntity userEntity2 = new UserEntity("Producer", "Jørgen", "Hans", "Producermand2", new Date(), "Hans@email.com");
        UserEntity userEntity3 = new UserEntity("Producer", "Bent", "Karl", "Producermand3", new Date(), "Hans@email.com");
        UserEntity userEntity6 = new UserEntity("Producer", "Mogens", "Søren", "Producermand6", new Date(), "Hans@email.com");

        List<UserEntity> users = new ArrayList<UserEntity>();
        users.add(userEntity1);
        users.add(userEntity2);
        users.add(userEntity3);
        users.add(userEntity6);

        return users;
    }

    /**
     * Enables user to choose a producer from a comboBox
     */
    public void chooseProducer()
    {
        chooseProducer.getItems().addAll(producerCreatorTest());

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
    public List<CreditEntity> creditCreatorTest()
    {
        UserEntity credit1 = new UserEntity("Lydmand", "Hans", "Hans", "Jørgensen", new Date(), "Hans@email.com");
        CreditEntity creditEntity1 = new CreditEntity(0,credit1);
        UserEntity credit2 = new UserEntity("Kameramand", "Bo", "Jørgen", "Hansen", new Date(), "Hans@email.com");
        CreditEntity creditEntity2 = new CreditEntity(0,credit2);

        List<CreditEntity> credits = new ArrayList<CreditEntity>();
        credits.add(creditEntity1);
        credits.add(creditEntity2);
        return credits;
    }

    /**
     * Enables user to choose a credit from a comboBox
     */
    public void chooseCredit()
    {
        chooseCredit.getItems().addAll(creditCreatorTest());

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

        String title;
        title = getTitle();

        String description;
        description = getDescription();

        ProgramEntity program = new ProgramEntity(programId, title, description, company, producers, credits);

        programEntity = program;
        domainHandler.program().updateProgram(program);
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
        updateProgTitle.setText(LanguageHandler.getText("titleHeader"));
        updateInsertTitle.setPromptText(LanguageHandler.getText("insertTitle"));
        updateProgDescription.setText(LanguageHandler.getText("descriptionHeader"));
        updateInsertDescription.setPromptText(LanguageHandler.getText("insertDescription"));
        updateProgCompany.setText(LanguageHandler.getText("programCompany"));
        updateProgCredits.setText(LanguageHandler.getText("programCredits"));
        updateCreditBtn.setText(LanguageHandler.getText("createCreditStageTitle"));
        updateProgProducer.setText(LanguageHandler.getText("producer"));
        addCreditButton.setText(LanguageHandler.getText("add"));
        addCreditHeader.setText(LanguageHandler.getText("addCreditHeader"));

    }
}
