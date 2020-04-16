package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.App;
import org.example.domain.DomainHandler;
import org.example.domain.Program;
import org.example.entity.CreditEntity;
import org.example.entity.ProgramEntity;
import org.example.entity.Role;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProgramInformationController implements Initializable {
    private DomainHandler domainHandler = new DomainHandler();
    public ProgramEntity programEntity;
    public TextArea infoTitle;
    public TextArea infoCompany;
    public TextArea infoProducer;
    public TextArea infoDescription;
    public TextArea infoCredits;
    public VBox programInfo;
    public Button updateProgInfoBtn;
    public Button deleteProgBtn;
    public Label companyInfoHeader;
    public Label producerInfoHeader;
    public Label creditInfoHeader;
    public Button cancelBtn;
    public ProgramListController programListController;



    /**
     * Opens "programInformation.fxml" as a popup scene
     * @param programEntityObject of the program that should open
     */
    public void openView(ProgramEntity programEntityObject)
    {
        Parent root = null;
        FXMLLoader loader = null;

        try {
            loader = App.getLoader("programInformation");
            root = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (loader == null) return;
        ProgramInformationController programInformationController = loader.getController();
        this.programEntity = domainHandler.program().getProgramById(programEntityObject);
        programInformationController.programEntity = this.programEntity;

        if (programEntity != null) {
            programInformationController.infoTitle.setText(programEntity.getName());
            programInformationController.infoDescription.setText(programEntity.getDescription());
        }

        if (programEntity != null && programEntity.getCompany() != null) {
            programInformationController.infoCompany.setText(programEntity.getCompany().getName());
        }

        if (programEntity != null && programEntity.getProducer() != null) {
            for (int i = 0; i < programEntity.getProducer().size(); i++) {
                programInformationController.infoProducer.appendText(programEntity.getProducer().get(i).getFullName() + "\n");
            }
        }

        if (programEntity != null && programEntity.getCredits() != null) {
            for (int i = 0; i < programEntity.getCredits().size(); i++) {
                programInformationController.infoCredits.appendText(programEntity.getCredits().get(i).getActor().getNameAndTitle() + "\n");
            }
        }

        programInformationController.programInfo.setMinWidth(400);

        programInformationController.updateProgInfoBtn.setText(LanguageHandler.getText("updateProgram"));
        programInformationController.deleteProgBtn.setText(LanguageHandler.getText("deleteProgram"));
        programInformationController.companyInfoHeader.setText(LanguageHandler.getText("companyInfoHeader"));
        programInformationController.producerInfoHeader.setText(LanguageHandler.getText("producerInfoHeader"));
        programInformationController.creditInfoHeader.setText(LanguageHandler.getText("creditHeader"));
        programInformationController.cancelBtn.setText(LanguageHandler.getText("cancel"));
        programInformationController.infoTitle.setEditable(false);
        programInformationController.infoCompany.setEditable(false);
        programInformationController.infoProducer.setEditable(false);
        programInformationController.infoDescription.setEditable(false);
        programInformationController.infoCredits.setEditable(false);

        /*if (CurrentUser.getInstance().getUserEntity() != null && CurrentUser.getInstance().getUserEntity().getRole() != Role.Actor) {
            updateProgInfoBtn.setVisible(true);
            deleteProgBtn.setVisible(true);
        }*/

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle(LanguageHandler.getText("programInformationStageTitle"));
        stage.setScene(scene);
        stage.setResizable(false);

        stage.showAndWait();
    }

    public void updateProgramInformation(ActionEvent event) throws IOException {
        UpdateProgramController updateProgramController = new UpdateProgramController();
        this.programEntity = updateProgramController.openView(programEntity);
        /*DefaultController defaultController = new DefaultController();
        if (programEntity != null)
        {
            //programListController.programEntityList.add(programEntity);
            defaultController.loadProgramList();
            //programListController.updateProgramList();
            //programListController.showProgramList(programEntity);
        }*/
        ControllerUtility.closeProgram(event);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    public void deleteProgram(ActionEvent event) throws IOException {
        domainHandler.program().deleteProgram(this.programEntity);
        closeProgramInformation(event);
    }

    @FXML
    private void closeProgramInformation(ActionEvent event)
    {
        ControllerUtility.closeProgram(event);
        //Men den her skal jo egentlig lukke det specifikke "programInformation" og ikke bare den nuværende scene. :angry:
    }
}
