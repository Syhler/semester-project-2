package org.example.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.domain.DomainHandler;
import org.example.entity.CompanyEntity;
import org.example.entity.CreditEntity;
import org.example.entity.ProgramEntity;
import org.example.entity.UserEntity;

public class ProgramListController implements Initializable {
    public ScrollPane scrollPane;
    private DomainHandler domainHandler;
    @FXML
    public VBox programList;

    public Node openView() {
        FXMLLoader loader = null;
        try {
            loader = App.getLoader("programList");
            Parent node = loader.load();
            return node;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Node showProgramList(ProgramEntity programEntity)
    {
        FXMLLoader loader = null;
        try {
            loader = App.getLoader("program");
            Parent node = loader.load();
            ProgramController programController = loader.<ProgramController>getController();
            programController.title.setText(programEntity.getName());
            programController.title.prefWidthProperty().bind(programList.widthProperty());
            //programController.title.prefHeightProperty().bind(programList.heightProperty()); //Højde unødvendigt at ændre her, da den har en fast, der passer.
            programController.title.setMaxWidth(850); //De her tal giver ingen mening
            //programController.title.setMinWidth(50);

            programController.description.setText(programEntity.getDescription());
            programController.description.prefWidthProperty().bind(programList.widthProperty());
            //programController.description.prefHeightProperty().bind(programList.heightProperty());
            //programController.title.setMinWidth(100);


            programController.buttonHolder.prefWidthProperty().bind(programList.widthProperty());
            //programController.buttonHolder.prefHeightProperty().bind(programList.heightProperty());
            programController.buttonHolder.setMaxWidth(250);
            programController.buttonHolder.setMinWidth(100);
            return node;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Test metode der udfylder et ProgramEntity med de ting den skal have
    public List<ProgramEntity> test() {
        CompanyEntity company = new CompanyEntity("TV2");

        UserEntity producer = new UserEntity("Producer", "Hans", "Jørgen", "Jørgensen", new Date(), "Hans@email.com");
        List<UserEntity> producerList = new ArrayList<UserEntity>();
        producerList.add(producer);

        CreditEntity credits = new CreditEntity("673267", producer);
        List<CreditEntity> creditList = new ArrayList<CreditEntity>();
        creditList.add(credits);

        List<ProgramEntity> programmer = new ArrayList<ProgramEntity>();

        for (int i = 1; i <= 50; i++)
        {
            ProgramEntity programEntity = new ProgramEntity("Søren på bakken med sin flotte hund, hvor de pøller sammen, planlægger deres dag, " +
                    "og tænker over hvordan de dog er nået til dette punkt i livet. " + i,
                    "Søren går ud på bakken, sammen med sin flotte hund: 'Hans' Søren går ud på bakken, sammen med sin flotte hund: 'Hans' " +
                            "Søren går ud på bakken, sammen med sin flotte hund: 'Hans' Søren går ud på bakken, sammen med sin flotte hund: 'Hans' " +
                            "Søren går ud på bakken, sammen med sin flotte hund: 'Hans' " +
                            "Søren går ud på bakken, sammen med sin flotte hund: 'Hans' " + i, company, producerList, creditList);
            programmer.add(programEntity);
        }

        return programmer;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        scrollPane.setFitToWidth(true);

        List<ProgramEntity> k = test();
        for (int i = 0; i < test().size(); i++)
        {
            programList.getChildren().add(showProgramList(k.get(i)));
        }
    }
}