package org.example.presentation.program;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.domain.applicationFacade.DomainFacade;
import org.example.domain.buisnessComponents.Company;
import org.example.domain.buisnessComponents.Program;
import org.example.domain.buisnessComponents.User;
import org.example.presentation.DefaultController;
import org.example.presentation.multipleLanguages.LanguageHandler;
import org.example.presentation.utilities.ControllerUtility;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgramManagementController implements Initializable {
    private DomainFacade domainHandler = new DomainFacade();
    @FXML
    private Label inactiveProgramsLabel;
    @FXML
    private ListView<Program> inactiveProgramsList;
    @FXML
    private Button reactivateProgramsBtn;
    @FXML
    private Label statusText;
    @FXML
    private Button cancelBtn;

    private final ObservableList<Program> deletedProgramEntities = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inactiveProgramsLabel.setText(LanguageHandler.getText("inactivePrograms"));
        reactivateProgramsBtn.setText(LanguageHandler.getText("unDelete"));
        cancelBtn.setText(LanguageHandler.getText("cancel"));

        setListCellFactory(inactiveProgramsList);

        var thread = new Thread(loadAllInactivePrograms());
        thread.start();

    }

    public void openView(ActionEvent event)
    {
        var programManagementStage = new Stage();

        try {
            FXMLLoader myLoader = App.getLoader("programManagement");
            programManagementStage.setScene(new Scene(myLoader.load()));
            programManagementStage.setTitle(LanguageHandler.getText("programManagement"));
            programManagementStage.initModality(Modality.WINDOW_MODAL);
            programManagementStage.initOwner(((Node) event.getTarget()).getScene().getWindow());
            programManagementStage.getIcons().add(new Image(App.class.getResourceAsStream("loginImages/tv2trans.png")));
            programManagementStage.setResizable(false);
            programManagementStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setStatusText(String text) {
        statusText.setText(text);
        statusText.setVisible(true);
    }

    private Runnable loadAllInactivePrograms()
    {
        return () ->
        {
            Platform.runLater(() -> setStatusText("Loading..."));

            var deletedPrograms = domainHandler.getAllDeletedProgram();
            if (deletedPrograms != null) {
                deletedProgramEntities.addAll(deletedPrograms);
            }

            Platform.runLater(()->
            {
                inactiveProgramsList.setItems(deletedProgramEntities);
                setStatusText("");
            });
        };
    }

    private void setListCellFactory(ListView<Program> listView)
    {
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Program item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getProgramInformation().getTitle());
                }
            }
        });
    }

    @FXML
    private void cancel(ActionEvent event)
    {
        ControllerUtility.closeProgram(event);
    }

    /**
     * Reactivates a selected program, and updates the list of programs
     * @param event
     */
    @FXML
    private void unDeletePrograms(ActionEvent event)
    {
        Program selectedProgram = inactiveProgramsList.getSelectionModel().getSelectedItem();

        boolean programWasRemoved = selectedProgram.unDelete();

        if (!programWasRemoved) //??????????????????????????
        {
            deletedProgramEntities.remove(selectedProgram);
            setStatusText(selectedProgram.getProgramInformation().getTitle()+LanguageHandler.getText("programWasReactivated"));
        } else {
            setStatusText(selectedProgram.getProgramInformation().getTitle()+LanguageHandler.getText("programWasNotReactivated"));
        }

        try {
            ProgramListController.getInstance().listOfPrograms.add(selectedProgram);
            ProgramListController.getInstance().updateProgramList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ControllerUtility.closeProgram(event);
    }
}
