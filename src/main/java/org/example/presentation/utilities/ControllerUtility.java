package org.example.presentation.utilities;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.domain.applicationFacade.DomainFacade;
import org.example.domain.buisnessComponents.Program;
import org.example.domain.io.Import;
import org.example.presentation.dialogControllers.DialogController;
import org.example.presentation.dialogControllers.ImportDialogController;
import org.example.presentation.multipleLanguages.LanguageHandler;
import org.example.presentation.program.ProgramListController;

import java.io.File;

public class ControllerUtility {

    /**
     * Closes the scene when a button is clicked
     * @param event
     */
    public static void closeProgram(ActionEvent event)
    {
        //gets the node of the given event
        Node source = (Node)  event.getSource();
        //gets that nodes stage
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Sets max character size of a text area, based on a given max size.
     * @param textArea that you want to set a limit on
     * @param maxSize int variable with a given size you want the text area to be limited to
     */
    public static void maxTextSize(TextArea textArea, int maxSize)
    {
        textArea.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= maxSize ? change : null));  //https://stackoverflow.com/questions/36612545/javafx-textarea-limit
    }

    /**
     * Finds the remaining characters available in a text area, based on a given max size of the text area.
     * @param textArea that you want to check remaining number of characters on
     * @param maxSize int variable with a given size you want the text area to be limited to
     * @return Number of remaining characters available, plus an explaining text
     */
    public static String remainingCharacters(TextArea textArea, int maxSize)
    {
        String remainingChars = LanguageHandler.getText("remainingCharacters");
        int remaining = maxSize - textArea.getLength();

        return remaining +" "+ remainingChars;
    }

    public static boolean gotAccessToProgram(Program program)
    {
        return CurrentUser.getInstance().getUser() != null && CurrentUser.getInstance().gotAccessToProgram(program);
    }

    public static void importProgram(ActionEvent event) throws Exception {
        var selectedFile = getFileFromFileChoose();

        var dialogController = new DialogController();

        if (selectedFile == null)
        {
            dialogController.openDialog(event, LanguageHandler.getText("noFile"), "Import Dialog");
            return;
        }


        var loadedPrograms = Import.loadPrograms(selectedFile);


        if (loadedPrograms.isEmpty())
        {
            dialogController.openDialog(event, LanguageHandler.getText("noProgramsImported"), "Import Dialog");
        }
        else
        {
            var programsToAdd = new ImportDialogController().openDialog(event, loadedPrograms);

            ProgramListController.getInstance().listOfPrograms.addAll(programsToAdd);
            ProgramListController.getInstance().updateProgramList();
        }

    }

    /**
     * open a fileChooser and return the file
     * @return the file the user have chosen from the file chooser
     */
    private static File getFileFromFileChoose()
    {
        var fileChooserStage = new Stage();

        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));

        return fileChooser.showOpenDialog(fileChooserStage);
    }
}
