package org.example.presentation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.domain.Credit;
import org.example.domain.DomainHandler;
import org.example.entity.CompanyEntity;
import org.example.entity.CreditEntity;
import org.example.entity.ProgramEntity;
import org.example.entity.UserEntity;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ProgramController implements Initializable {
    public Button showMore;
    public Button updateProgram;
    public Button deleteProgram;
    public Label title;
    private DomainHandler domainHandler;



    public ProgramEntity test() {

        CompanyEntity company = new CompanyEntity("TV2");


        UserEntity producer = new UserEntity("Producer", "Hans", "Jørgen", "Jørgensen", new Date(), "Hans@email.com");
        List<UserEntity> producerList = new ArrayList<UserEntity>();
        producerList.add(producer);


        CreditEntity credits = new CreditEntity("673267", producer);
        List<CreditEntity> creditList = new ArrayList<CreditEntity>();
        creditList.add(credits);



        ProgramEntity programEntity = new ProgramEntity("Søren på bakken med sin flotte hund",
                "Søren går ud på bakken", company, producerList, creditList);

        return programEntity;

    }

    @FXML
    private void goToCredit() throws IOException {

    }

    @FXML
    private void createProgram() throws IOException {
    }

    @FXML
    private void deleteProgram() throws IOException {
    }

    @FXML
    private void updateProgram() throws IOException {
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showMore.setText(LanguageHandler.getText("showMore"));
        updateProgram.setText(LanguageHandler.getText("updateProgram"));
        deleteProgram.setText(LanguageHandler.getText("deleteProgram"));

        //Test
        title.setText(test().getName());

    }
}
