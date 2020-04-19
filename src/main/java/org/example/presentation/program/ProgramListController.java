package org.example.presentation.program;

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
import org.example.OLDentity.ProgramEntity;

public class ProgramListController implements Initializable {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane listGridPane;

    @FXML
    private VBox programList;

    public List<ProgramEntity> programEntityList = new ArrayList<>();

    private static ProgramListController programListController = null;

    /**
     * Singleton
     * @return current active controller
     * @throws Exception
     */
    public static ProgramListController getInstance() throws Exception
    {
        if (programListController != null)
        {
            return programListController;
        } else {
            throw new Exception("programListController is null");
        }
    }

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

    /**
     * Updates a programEntity in the list, so that correct information is showed in the ui
     * @param programEntity of the program you want to update
     */
    public void updateProgramInList(ProgramEntity programEntity) {
        for (ProgramEntity entity : programEntityList) {
            if (entity.getId() == programEntity.getId()) {
                entity.setName(programEntity.getName());
                break;
            }
        }
    }

    /**
     * Removes a programEntity from the list, so that correct inforamtion is showed in the ui
     * @param programEntity of the program you want to delete
     */
    public void removeProgramFromList(ProgramEntity programEntity)
    {
        for (int i = 0; i < programEntityList.size(); i++)
        {
            if (programEntityList.get(i).getId() == programEntity.getId()) {
                programEntityList.remove(i);
                break;
            }
        }
    }

    /**
     * Updates the program list, by adding rows and columns to the gridpane, so the list fits with the programs in it
     */
    public void updateProgramList()
    {
        listGridPane.getChildren().clear();

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
        ProgramListController.programListController = this;
    }
}