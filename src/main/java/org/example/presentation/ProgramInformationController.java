package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.example.App;
import org.example.domain.DomainHandler;
import org.example.entity.CreditEntity;
import org.example.entity.ProgramEntity;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgramInformationController implements Initializable {
    private DomainHandler domainHandler = new DomainHandler();
    public ProgramEntity programEntity;

    /**
     * Opens "programInformation.fxml" as a popup scene
     * @param programEntity of the program that should open
     */
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

        programEntity = domainHandler.program().getProgramById(programEntity);

        if (programEntity.getName() != null) {
            programController.infoTitle.setText(programEntity.getName());
        }

        if (programEntity.getCompany() != null) {
            programController.infoCompany.setText(programEntity.getCompany().getName());
        }

        if (programEntity.getDescription() != null) {
            programController.infoDescription.setText(programEntity.getDescription());
        }

        if (programEntity.getProducer() != null) {
            for (int i = 0; i < programEntity.getProducer().size(); i++) {
                programController.infoProducer.appendText(programEntity.getProducer().get(i).getName() + "\n");
            }
        }

        if (programEntity.getCredits() != null) {
            for (int i = 0; i < programEntity.getCredits().size(); i++) {
                programController.infoCredits.appendText(programEntity.getCredits().get(i).getActor().getNameAndTitle() + "\n");
            }
        }

        programController.programInfo.setMinWidth(400);

        programController.updateProgInfoBtn.setText(LanguageHandler.getText("updateProgram"));
        programController.deleteProgBtn.setText(LanguageHandler.getText("deleteProgram"));
        programController.companyInfoHeader.setText(LanguageHandler.getText("companyInfoHeader"));
        programController.producerInfoHeader.setText(LanguageHandler.getText("producerInfoHeader"));
        programController.creditInfoHeader.setText(LanguageHandler.getText("creditHeader"));
        programController.cancelBtn.setText(LanguageHandler.getText("cancel"));
        programController.infoTitle.setEditable(false);
        programController.infoCompany.setEditable(false);
        programController.infoProducer.setEditable(false);
        programController.infoDescription.setEditable(false);
        programController.infoCredits.setEditable(false);

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle(LanguageHandler.getText("programInformationStageTitle"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }

    /*public void updateProgramInformation(ActionEvent event) throws IOException {

        UpdateProgramController updateProgramController = new UpdateProgramController();
        this.programEntity = updateProgramController.openView(programEntity);
        ControllerUtility.closeProgram(event);
    }*/


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
