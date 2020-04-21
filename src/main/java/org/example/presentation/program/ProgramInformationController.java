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
import org.example.domain.Credit;
import org.example.domain.DomainFacade;
import org.example.domain.Program;
import org.example.domain.User;
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
    private ListView<Credit> creditListView;

    @FXML
    private ListView<User> producersListView;

    @FXML
    private ListView<Credit> actorListView;

    private DomainFacade domainHandler = new DomainFacade();
    public Program program;
    public VBox programInfo;



    /**
     * Opens "programInformation.fxml" as a popup scene
     * @param programObject of the program that should open
     */
    public void openView(Program programObject)
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

        programInformationController.program = domainHandler.getProgramById(programObject.getId());

        if (programInformationController.program != null)
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
        var tempProgram = programInformationController.program;

        programInformationController.title.setText(tempProgram.getProgramInformation().getTitle());
        programInformationController.descriptionTextArea.setText(tempProgram.getProgramInformation().getDescription());
        if (tempProgram.getCompany() != null) {
            programInformationController.productionCompany.setText(LanguageHandler.getText("companyInfoHeader") + ": " + tempProgram.getCompany().getName());
        }

        if (tempProgram.getProducers() != null) {
            for (int i = 0; i < tempProgram.getProducers().size(); i++)
            {
                programInformationController.producersListView.getItems().add(tempProgram.getProducers().get(i));
                //programInformationController.infoProducer.appendText(programEntity.getProducer().get(i).getFullName() + "\n");
            }
        }

        if (tempProgram.getCredits() != null) {
            for (int i = 0; i < tempProgram.getCredits().size(); i++) {

                var credit = tempProgram.getCredits().get(i);
                if (credit.getUser() == null) continue;

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

        var exportedPrograms = program.export(file.getPath());

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
        if (!ControllerUtility.gotAccessToProgram(program))
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

    private void setListCellFactoryForCredit(ListView<Credit> listView)
    {
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Credit item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getUser() == null) {
                    setText(null);
                } else {
                    setText(item.getUser().getFullName() + ": " + item.getUser().getTitle());
                }
            }
        });
    }

    private void setListCellFactory(ListView<User> listView)
    {
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getFullName() + ": " + item.getTitle());
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
        this.program = updateProgramController.openView(program);

        if (program != null)
        {
            setupText(this);
            //domainHandler.program().updateProgram(program);

            try {
                ProgramListController.getInstance().updateProgramInList(program);
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
        //domainHandler.program().deleteProgram(this.program);
        program.delete();

        if (program != null)
        {
            try {
                ProgramListController.getInstance().removeProgramFromList(program);
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
        //var deleted = domainHandler.program().removeCreditFromProgram(selectedActor);
        var wasDeleted = program.deleteCredit(selectedActor);

        if (!wasDeleted) return;
        if (selectedActor == null) return;

        actorListView.getItems().remove(selectedActor);
    }

    public void producerOnDelete(ActionEvent event)
    {
        var selectedProducer = producersListView.getSelectionModel().getSelectedItem();
        var wasDeleted = program.deleteProducer(selectedProducer);

        if (!wasDeleted) return;
        if (selectedProducer == null) return;

        producersListView.getItems().remove(selectedProducer);
    }

    public void creditOnDelete(ActionEvent event) {

        var selectedCredit = creditListView.getSelectionModel().getSelectedItem();
        //var deleted = domainHandler.program().removeCreditFromProgram(selectedActor);
        var wasDeleted = program.deleteCredit(selectedCredit);

        if (!wasDeleted) return;
        if (selectedCredit == null) return;

        creditListView.getItems().remove(selectedCredit);
    }

    public void descriptionClick(MouseEvent mouseEvent)
    {
        //Maybe for the future
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){

                if (!ControllerUtility.gotAccessToProgram(program)) return;

                descriptionTextArea.setEditable(true);

            }
        }
    }


}