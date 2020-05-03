package org.example.presentation.dialogControllers;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.domain.applicationFacade.DomainFacade;
import org.example.domain.buisnessComponents.Program;
import org.example.presentation.utilities.ControllerUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportController
{

    public ProgressBar progress_bar;

    @FXML
    private Button okButton;

    @FXML
    private Label statusLabel;

    private List<Program> programList = new ArrayList<>();

    private SimpleStringProperty textProp = new SimpleStringProperty();

    public void setStatusLabelText(String statusText) {
        this.statusLabel.setText(statusText);
    }

    /**
     * Open a importDialog
     * @param text the text you want to display on the popup dialog.
     */
    public List<Program> openDialog(ActionEvent event, String text, String title, List<Program> programs)
    {
        var dialogStage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("dialog/importDialog.fxml"));

            dialogStage.setScene(new Scene(loader.load()));
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(((Node)event.getTarget()).getScene().getWindow());
            dialogStage.setResizable(false);

            ImportController controller = loader.getController();
            controller.statusLabel.textProperty().bindBidirectional(controller.textProp);
            controller.progress_bar.setProgress(0.5F);

            dialogStage.getIcons().add(new Image(App.class.getResourceAsStream("loginImages/tv2trans.png")));
            if (programs != null)
            {
                var thread = createImportThread(controller, programs);
                thread.start();
            }
            dialogStage.showAndWait();

            return controller.programList;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*

     */

    private Thread createImportThread(ImportController controller, List<Program> programs)
    {
        Thread thread = new Thread(() -> {

        for (int i = 0; i < programs.size(); i++)
        {
            float finalI = i+1;

            var program = new DomainFacade().importPrograms(programs.get(i));

            Platform.runLater(() -> {
                    controller.textProp.setValue((int)finalI + "/" + programs.size() + " program(s) imported...");
                    controller.progress_bar.setProgress(finalI/programs.size());
                    System.out.println(finalI + "/100");
            });

            if (program != null)
            {
                controller.programList.add(program);
            }
        }

        Platform.runLater(() -> {
            controller.okButton.setDisable(false);
            controller.textProp.setValue("Completed...");
        });

        });
        thread.setDaemon(true);
        return thread;
    }

    /**
     * what happen when clicking the ok button
     */
    @FXML
    public void okOnAction(ActionEvent event)
    {
        ControllerUtility.closeProgram(event);
    }
}
