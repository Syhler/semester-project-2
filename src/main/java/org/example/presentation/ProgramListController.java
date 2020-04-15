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
    public List<ProgramEntity> programEntityList = new ArrayList<ProgramEntity>();

    /**
     * Loads the "program.fxml", gives it a title based on the given ProgramEntity
     * @param programEntity
     * @return
     */
    public Node showProgramList(ProgramEntity programEntity)
    {
        if (programEntity == null)
        {
            return null;
        }
        FXMLLoader loader = null;
        try {
            loader = App.getLoader("program");
            Parent node = loader.load();
            ProgramController programController = loader.<ProgramController>getController();
            programController.title.setText(programEntity.getName());
            programController.program.prefWidthProperty().bind(listGridPane.widthProperty());
            //programController.program.setMaxWidth(240);
            programController.programEntity = programEntity;

            return node;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<ProgramEntity> programList() {

        List<ProgramEntity> programmer = new ArrayList<ProgramEntity>();

        return programmer;
    }

    /**
     * Updates the program list, by adding rows and columns to the gridpane, so the list fits with the programs in it
     */
    public void updateProgramList()
    {
        int rowSize = 0;
        int columnSize = 0;

        for (int i = 0; i < programEntityList.size(); i++)
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

            listGridPane.add(showProgramList(programEntityList.get(i)),columnSize,rowSize);
        }
    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        scrollPane.setFitToWidth(true);

    }
}