package org.example.presentation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
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


    @FXML
    private void closeUpdateProgram(ActionEvent event)
    {
        ControllerUtility.closeProgram(event);
    }

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

    public List<CompanyEntity> companyTest()
    {
        CompanyEntity companyTV2 = new CompanyEntity("TV2");
        CompanyEntity companyDR1 = new CompanyEntity("DR1");
        CompanyEntity companyBlue = new CompanyEntity("Blue");
        List<CompanyEntity> companies = new ArrayList<>();
        companies.add(companyBlue);
        companies.add(companyTV2);
        companies.add(companyDR1);
        return companies;
    }

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

    public void addProducer(ActionEvent event)
    {
        UserEntity producer = chooseProducer.getSelectionModel().getSelectedItem();
        producerList.appendText(producer.getName()+"\n");
        producers.add(chooseProducer.getSelectionModel().getSelectedItem());
    }


    @FXML
    public void goToCreateCredit(ActionEvent event) throws IOException {
        CreditController creditController = new CreditController();
        UserEntity credit = creditController.openView();
        creditList.appendText(credit.getName() +" - "+ credit.getTitle() +"\n");
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

    @FXML
    public ProgramEntity updateProgram(ActionEvent event)
    {
        company = chooseCompany.getSelectionModel().getSelectedItem();

        String title;
        title = getTitle();

        String description;
        description = getDescription();

        ProgramEntity program = new ProgramEntity(title, description, company, producers, credits);

        System.out.println(program.toString());

        ProgramListController programListController = new ProgramListController();
        programListController.programList().add(program);
        programListController.updateProgramList();

        closeUpdateProgram(event);
        return program;
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

    }
}
