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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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
    public List<ProgramEntity> test() {
        CompanyEntity company = new CompanyEntity("TV2");

        UserEntity producer = new UserEntity("Producer", "Hans", "Jørgen", "Jørgensen", new Date(), "Hans@email.com");
        List<UserEntity> producerList = new ArrayList<UserEntity>();
        producerList.add(producer);

        CreditEntity credit = new CreditEntity("673267", producer);
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
        }
        return programmer;
    }

    public ProgramEntity updateProgram()
    {
        UpdateProgramController updateProgramController = new UpdateProgramController();
        CompanyEntity company = new CompanyEntity((String) updateProgramController.chooseCompany.getSelectionModel().getSelectedItem());

        List<UserEntity> producerList = new ArrayList<UserEntity>();
        producerList.add((UserEntity) updateProgramController.producerCreatorTest());

        String title;
        title = updateProgramController.getTitle();

        String description;
        description = updateProgramController.getDescription();

        CreditEntity credit = new CreditEntity("?", );
        List<CreditEntity> creditList = new ArrayList<CreditEntity>();
        creditList.add(credit);

        ProgramEntity program = new ProgramEntity(title, description, company, producerList, creditList);

        return program;
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        scrollPane.setFitToWidth(true);

        List<ProgramEntity> k = test();
        int rowSize = 0;
        int columnSize = 0;

        for (int i = 0; i < test().size(); i++)
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
}