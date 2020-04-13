package org.example.presentation;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.example.App;
import org.example.entity.ProgramEntity;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgramInformationController implements Initializable {


    public void openView(ProgramEntity programEntity)
    {
        Parent root = null;
        FXMLLoader loader = null;

        try {
            loader = App.getLoader("programInformation");
            root = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        ProgramController programController = loader.<ProgramController>getController();

        programController.infoTitle.setText(programEntity.getName());
        programController.infoCompany.setText(programEntity.getCompany().getName());
        programController.infoDescription.setText(programEntity.getDescription());
        programController.infoProducer.setText(programEntity.getProducer().toString());
        programController.infoCredits.setText(programEntity.getCredits().toString());
        programController.programInfo.setMinWidth(400);

        programController.updateProgInfoBtn.setText(LanguageHandler.getText("updateProgram"));
        programController.deleteProgBtn.setText(LanguageHandler.getText("deleteProgram"));
        programController.infoTitle.setEditable(false);
        programController.infoCompany.setEditable(false);
        programController.infoProducer.setEditable(false);
        programController.infoDescription.setEditable(false);
        programController.infoCredits.setEditable(false);

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle(LanguageHandler.getText("programInformationStageTitle"));
        stage.setScene(scene);
        stage.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
