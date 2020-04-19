package org.example.presentation.utilities;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import org.example.OLDentity.ProgramEntity;
import org.example.presentation.CurrentUser;
import org.example.presentation.multipleLanguages.LanguageHandler;

public class ControllerUtility {

    /**
     * Closes the scene when a button is clicked
     * @param event
     */
    public static void closeProgram(ActionEvent event)
    {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
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

    public static boolean gotAccessToProgram(ProgramEntity programEntity)
    {
        return CurrentUser.getInstance().getUserEntity() != null && CurrentUser.getInstance().gotAccessToProgram(programEntity);
    }
}
