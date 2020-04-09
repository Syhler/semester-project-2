package org.example.presentation;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.App;
import org.example.domain.DomainHandler;
import org.example.domain.Export;
import org.example.domain.Import;
import org.example.presentation.dialogControllers.ImportExportDialogController;
import org.example.presentation.multipleLanguages.LanguageHandler;

public class DefaultController
{

    @FXML
    private Button login;
    private DomainHandler domainHandler = new DomainHandler();

    @FXML
    private void goToLogin(ActionEvent event)
    {
        AuthenticationController authenticationController = new AuthenticationController();
        var userEntity = authenticationController.openLoginStage(event);

        if (userEntity != null)
        {
            login.setText("Logout");
            login.setOnAction(this::logout);
        }
    }

    private void logout(ActionEvent event)
    {
        if (CurrentUser.getInstance().getUserEntity() != null)
        {
            CurrentUser.getInstance().init(null); //Logs off
            login.setText("Login");
            login.setOnAction(this::goToLogin);
        }
    }

    @FXML
    private void export(ActionEvent event) {
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

        var exportedPrograms = Export.program(App.dummyData(), file.getPath());

        if (exportedPrograms != null)
        {
            controller.openDialog(event, LanguageHandler.getText("succeedExport"), "Export Dialog");
        }
        else
        {
            controller.openDialog(event, LanguageHandler.getText("noExport"), "Export Dialog");
        }

    }

    @FXML
    private void importFile(ActionEvent event)
    {

        var selectedFile = getFileFromFileChoose();

        ImportExportDialogController controller = new ImportExportDialogController();

        if (selectedFile == null)
        {
            controller.openDialog(event, LanguageHandler.getText("noFile"), "Import Dialog");
            return;
        }

        var loadedPrograms = Import.loadPrograms(selectedFile);

        if (loadedPrograms.isEmpty())
        {
            controller.openDialog(event, LanguageHandler.getText("noProgramsImported"), "Import Dialog");
        }
        else
        {
            controller.openDialog(event,
                    LanguageHandler.getText("succeedImport") + " " + loadedPrograms.size() + " " +
                            LanguageHandler.getText("programs"), "Import Dialog");
        }
    }


    /**
     * open a fileChooser and return the file
     * @return the file the user have chosen from the file chooser
     */
    private File getFileFromFileChoose()
    {
        var fileChooserStage = new Stage();

        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));

        return fileChooser.showOpenDialog(fileChooserStage);
    }

    @FXML
    private void goToSearch() throws IOException {

    }
    @FXML
    private void goToProgram() throws IOException {

    }
    @FXML
    private void goToUsermanagement() throws IOException {

    }
}
