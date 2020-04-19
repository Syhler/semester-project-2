package org.example.presentation.program;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.App;
import org.example.OLDdomain.DomainHandler;
import org.example.OLDdomain.Export;
import org.example.OLDentity.CreditEntity;
import org.example.OLDentity.ProgramEntity;
import org.example.OLDentity.UserEntity;
import org.example.presentation.utilities.ControllerUtility;
import org.example.presentation.dialogControllers.ImportExportDialogController;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgramInformationController implements Initializable {

    @FXML
    private ContextMenu producerListViewContextMenu;

    @FXML
    private ContextMenu creditsListViewContextMenu;

    @FXML
    private ContextMenu actorListViewContextMenu;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Label title;

    @FXML
    private Pane descriptionPane;

    @FXML
    private Label productionCompany;

    @FXML
    private TitledPane actorTitledPane;

    @FXML
    private TitledPane producerTitledPane;

    @FXML
    private TitledPane creditsTitledPane;

    @FXML
    private Button exportBtn;

    @FXML
    private ListView<CreditEntity> creditListView;

    @FXML
    private ListView<UserEntity> producersListView;

    @FXML
    private ListView<CreditEntity> actorListView;

    private DomainHandler domainHandler = new DomainHandler();
    public ProgramEntity programEntity;
    public VBox programInfo;



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

        if (loader == null || root == null) return;
        ProgramInformationController programInformationController = loader.getController();
        this.programEntity = domainHandler.program().getProgramById(programEntityObject);
        programInformationController.programEntity = this.programEntity;

        if (programEntity != null)
        {
            setupText(programInformationController);
        }



        programInformationController.actorTitledPane.setText(LanguageHandler.getText("actorInfoHeader"));
        programInformationController.producerTitledPane.setText(LanguageHandler.getText("producerInfoHeader"));
        programInformationController.creditsTitledPane.setText(LanguageHandler.getText("creditHeader"));

        programInformationController.updateBtn.setText(LanguageHandler.getText("updateProgram"));
        programInformationController.deleteBtn.setText(LanguageHandler.getText("deleteProgram"));


        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle(LanguageHandler.getText("programInformationStageTitle"));
        stage.setScene(scene);

        stage.showAndWait();
    }

    private void setupText(ProgramInformationController programInformationController)
    {
        programInformationController.title.setText(programEntity.getName());
        programInformationController.descriptionTextArea.setText(programEntity.getDescription());
        if (programEntity.getCompany() != null) {
            programInformationController.productionCompany.setText(LanguageHandler.getText("companyInfoHeader") + ": " + programEntity.getCompany().getName());
        }

        if (programEntity.getProducer() != null) {
            for (int i = 0; i < programEntity.getProducer().size(); i++)
            {
                programInformationController.producersListView.getItems().add(programEntity.getProducer().get(i));
                //programInformationController.infoProducer.appendText(programEntity.getProducer().get(i).getFullName() + "\n");
            }
        }

        if (programEntity.getCredits() != null) {
            for (int i = 0; i < programEntity.getCredits().size(); i++) {

                var credit = programEntity.getCredits().get(i);
                if (credit.getActor() == null) continue;

                programInformationController.creditListView.getItems().add(credit);
            }
        }
    }

    @FXML
    public void exportOnAction(ActionEvent event)
    {
        var fileChooserStage = new Stage();

        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));

        //ask the user where to save the file
        var file = fileChooser.showSaveDialog(fileChooserStage);

        ImportExportDialogController controller = new ImportExportDialogController();


        if (file == null)
        {
            controller.openDialog(event, LanguageHandler.getText("noSave"), "Export Dialog");
            return;
        }

        var exportedPrograms = Export.program(programEntity, file.getPath());

        if (exportedPrograms != null)
        {
            controller.openDialog(event, LanguageHandler.getText("succeedExport"), "Export Dialog");
        }
        else
        {
            controller.openDialog(event, LanguageHandler.getText("noExport"), "Export Dialog");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Sets update and delete button visible if logged in user has correct role
        if (!ControllerUtility.gotAccessToProgram(programEntity))
        {
            updateBtn.setVisible(false);
            deleteBtn.setVisible(false);
            actorListViewContextMenu.getItems().get(0).setVisible(false);
            producerListViewContextMenu.getItems().get(0).setVisible(false);
            creditsListViewContextMenu.getItems().get(0).setVisible(false);
            exportBtn.setVisible(false);

        }

        descriptionTextArea.prefWidthProperty().bind(descriptionPane.widthProperty());

        setListCellFactoryForCredit(actorListView);
        setListCellFactory(producersListView);
        setListCellFactoryForCredit(creditListView);


    }

    private void setListCellFactoryForCredit(ListView<CreditEntity> listView)
    {
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(CreditEntity item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getActor() == null) {
                    setText(null);
                } else {
                    setText(item.getActor().getNameAndTitle());
                }
            }
        });
    }

    private void setListCellFactory(ListView<UserEntity> listView)
    {
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(UserEntity item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getNameAndTitle() == null) {
                    setText(null);
                } else {
                    setText(item.getNameAndTitle());
                }
            }
        });
    }


    /**
     * Updates the current program, and updates the list, so the correct information is entered
     * @param event
     * @throws IOException
     */
    @FXML
    public void updateOnAction(ActionEvent event) throws IOException {
        UpdateProgramController updateProgramController = new UpdateProgramController();
        this.programEntity = updateProgramController.openView(programEntity);

        if (programEntity != null)
        {
            setupText(this);
            domainHandler.program().updateProgram(programEntity);

            try {
                ProgramListController.getInstance().updateProgramInList(programEntity);
                ProgramListController.getInstance().updateProgramList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    /**
     * Deletes the current program
     * @param event
     * @throws IOException
     */
    @FXML
    public void deleteOnAction(ActionEvent event) throws IOException {
        domainHandler.program().deleteProgram(this.programEntity);
        if (programEntity != null)
        {
            try {
                ProgramListController.getInstance().removeProgramFromList(programEntity);
                ProgramListController.getInstance().updateProgramList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ControllerUtility.closeProgram(event);

    }



    public void actorOnDelete(ActionEvent event)
    {
        var selectedActor = actorListView.getSelectionModel().getSelectedItem();
        var deleted = domainHandler.program().removeCreditFromProgram(selectedActor);

        if (!deleted) return;
        if (selectedActor == null) return;

        actorListView.getItems().remove(selectedActor);
    }

    public void producerOnDelete(ActionEvent event)
    {
        var selectedProducer = producersListView.getSelectionModel().getSelectedItem();
        var deleted = domainHandler.program().removeUserFromProgram(selectedProducer, programEntity.getId());

        if (!deleted) return;
        if (selectedProducer == null) return;

        producersListView.getItems().remove(selectedProducer);
    }

    public void creditOnDelete(ActionEvent event) {
        var selectedCredit = creditListView.getSelectionModel().getSelectedItem();
        var deleted = domainHandler.program().removeCreditFromProgram(selectedCredit);

        if (!deleted)return;
        if (selectedCredit == null) return;

        creditListView.getItems().remove(selectedCredit);
    }

    public void descriptionClick(MouseEvent mouseEvent)
    {
        //Maybe for the future
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){

                if (!ControllerUtility.gotAccessToProgram(programEntity)) return;

                descriptionTextArea.setEditable(true);

            }
        }
    }


}
