package org.example.presentation.program;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.App;
import org.example.domain.buisnessComponents.Program;

public class ProgramListController implements Initializable {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane listGridPane;


    @FXML
    private VBox programList;

    public List<Program> listOfPrograms = new ArrayList<>();

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
     * @param program
     * @return
     */
    public Node showProgramList(Program program)
    {
        if (program == null)
        {
            return null;
        }
        FXMLLoader loader = null;
        try {
            loader = App.getLoader("program");
            Parent node = loader.load();
            ProgramController programController = loader.getController();

            programController.title.setText(program.getProgramInformation().getTitle());
            programController.program.prefWidthProperty().bind(listGridPane.widthProperty());
            programController.programImage.setImage(program.getImage());
            programController.programEntity = program;

            return node;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates a programEntity in the list, so that correct information is showed in the ui
     * @param program of the program you want to update
     */
    public void updateProgramInList(Program program) {
        for (Program temp : listOfPrograms) {
            if (temp.getId() == program.getId()) {
                temp.getProgramInformation().setTitle(program.getProgramInformation().getTitle());
                break;
            }
        }
    }

    /**
     * Removes a programEntity from the list, so that correct inforamtion is showed in the ui
     * @param program of the program you want to delete
     */
    public void removeProgramFromList(Program program)
    {
        for (int i = 0; i < listOfPrograms.size(); i++)
        {
            if (listOfPrograms.get(i).getId() == program.getId()) {
                listOfPrograms.remove(i);
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

        var thread = new Thread(() -> {

            int rowSize = 0;
            int columnSize = 0;

            for (int i = 0; i < listOfPrograms.size(); i++)
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

                int finalColumnSize = columnSize;
                int finalRowSize = rowSize;
                int finalI = i;
                Platform.runLater(()->
                        {
                            listGridPane.add(showProgramList(listOfPrograms.get(finalI)), finalColumnSize, finalRowSize);
                            System.out.println("Showing list : " + finalI + " Column: " + listGridPane.getColumnCount() + " row: " + listGridPane.getRowCount());

                        });

            }

            Thread.currentThread().interrupt();
        });
        thread.setDaemon(true);
        thread.start();
    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        scrollPane.setFitToWidth(true);
        ProgramListController.programListController = this;
    }
}