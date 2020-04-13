package org.example.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.App;
import org.example.domain.DomainHandler;
import org.example.entity.ProgramEntity;

public class ProgramListController implements Initializable {
    public ScrollPane scrollPane;
    public GridPane listGridPane;
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
            programController.program.prefWidthProperty().bind(listGridPane.widthProperty());
            //programController.program.setMaxWidth(240);

            return node;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Test metode der udfylder et ProgramEntity med de ting den skal have
    public List<ProgramEntity> programList() {

        List<ProgramEntity> programmer = new ArrayList<ProgramEntity>();

        return programmer;

        /*long programID = 1;
        CompanyEntity company = new CompanyEntity("TV2");

        UserEntity producer = new UserEntity("Producer", "Hans", "Jørgen", "Jørgensen", new Date(), "Hans@email.com");
        List<UserEntity> producerList = new ArrayList<UserEntity>();
        producerList.add(producer);

        CreditEntity credit = new CreditEntity(programID, producer);
        List<CreditEntity> creditList = new ArrayList<CreditEntity>();
        creditList.add(credit);

        List<ProgramEntity> programmer = new ArrayList<ProgramEntity>();

        for (int i = 1; i <= 50; i++)
        {
            ProgramEntity programEntity = new ProgramEntity("Søren sidder på sin bakke og hygger. Han har sin hund med." + i,
                    "Søren går ud på bakken, sammen med sin flotte hund: 'Hans' Søren går ud på bakken, sammen med sin flotte hund: 'Hans' " +
                            "Søren går ud på bakken, sammen med sin flotte hund: 'Hans' Søren går ud på bakken, sammen med sin flotte hund: 'Hans' " +
                            "Søren går ud på bakken, sammen med sin flotte hund: 'Hans' " +
                            "Søren går ud på bakken, sammen med sin flotte hund: 'Hans' " + i, company, producerList, creditList);
            programmer.add(programEntity);
            programID++;
        }
        return programmer;*/
    }

    public void updateProgramList()
    {
        List<ProgramEntity> k = programList();
        int rowSize = 0;
        int columnSize = 0;

        for (int i = 0; i < programList().size(); i++)
        {
            if (i%4 == 0)
            {
                rowSize++;
            }
            if (i%4!= 0)
            {
                columnSize++;
            }
            if (i%4 == 0)
            {
                columnSize = 0;
            }

            listGridPane.add(showProgramList(k.get(i)),columnSize,rowSize);
        }
    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        scrollPane.setFitToWidth(true);
        //updateProgramList();

    }
}