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
import org.example.App;
import org.example.entity.CompanyEntity;
import org.example.entity.UserEntity;
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
    public ChoiceBox chooseCompany;
    public ChoiceBox chooseProducer;
    public TextArea creditList;
    public TextArea producerList;
    public Button addSelectedProducer;
    public String producer;


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

    public List<String> companyTest()
    {
        CompanyEntity companyTV2 = new CompanyEntity("TV2");
        CompanyEntity companyDR1 = new CompanyEntity("DR1");
        CompanyEntity companyBlue = new CompanyEntity("Blue");
        List<String> companies = new ArrayList<>();
        companies.add(companyBlue.getName());
        companies.add(companyTV2.getName());
        companies.add(companyDR1.getName());
        return companies;
    }

    public void chooseCompany()
    {
        chooseCompany.getItems().addAll(companyTest());
    }


    public List<UserEntity> producerCreatorTest()
    {

        UserEntity userEntity1 = new UserEntity("Producer", "Hans", "Jørgen", "Producermand1", new Date(), "Hans@email.com");
        UserEntity userEntity2 = new UserEntity("Producer", "Jørgen", "Hans", "Producermand2", new Date(), "Hans@email.com");
        UserEntity userEntity3 = new UserEntity("Producer", "Bent", "Karl", "Producermand3", new Date(), "Hans@email.com");
        UserEntity userEntity4 = new UserEntity("Kameramand", "Bo", "Jens", "Kameramand4", new Date(), "Hans@email.com");
        UserEntity userEntity5 = new UserEntity("Lydmand", "Karl", "Grøn", "Lydmand5", new Date(), "Hans@email.com");
        UserEntity userEntity6 = new UserEntity("Producer", "Mogens", "Søren", "Producermand6", new Date(), "Hans@email.com");

        List<UserEntity> users = new ArrayList<UserEntity>();
        users.add(userEntity1);
        users.add(userEntity2);
        users.add(userEntity3);
        users.add(userEntity4);
        users.add(userEntity5);
        users.add(userEntity6);

        return users;
    }

    public List<String> producerTest()
    {
        List<String> producerUsers = new ArrayList<>();

        for (int i = 0; i < producerCreatorTest().size(); i++)
        {
            if (producerCreatorTest().get(i).getTitle() == "Producer")
            {
                producerUsers.add(producerCreatorTest().get(i).getName());
            }
        }

        return producerUsers;
    }

    public void chooseProducer()
    {
        chooseProducer.getItems().addAll(producerTest());
    }

    public void addProducer(ActionEvent event)
    {
        producer = (String) chooseProducer.getSelectionModel().getSelectedItem() + "\n";
        producerList.appendText(producer);
    }


    @FXML
    public void goToCreateCredit(ActionEvent event) throws IOException {
        CreditController creditController = new CreditController();
        creditList.appendText(creditController.openView());
    }

    public String getTitle()
    {
        return updateInsertTitle.getText();
    }

    public String getDescription()
    {
        return updateInsertDescription.getText();
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
