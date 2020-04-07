package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import org.example.presentation.multipleLanguages.LanguageHandler;

public class ControllerUtility {
    public static void closeProgram(ActionEvent event)
    {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Sets max character size of a text area, based on a given max size.
     * @param textArea
     * @param maxSize
     */
    public static void maxDescriptionSize(TextArea textArea, int maxSize)
    {
        textArea.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= maxSize ? change : null));  //https://stackoverflow.com/questions/36612545/javafx-textarea-limit
    }

    /**
     * Finds the remaining characters available in a text area, based on a given max size of the text area.
     * @param textArea
     * @param maxSize
     * @return Number of remaining characters available, plus an explaining text
     */
    public static String remainingCharacters(TextArea textArea, int maxSize)
    {
        String remainingChars = LanguageHandler.getText("remainingCharacters");
        int remaining = maxSize - textArea.getLength();

        return remaining +" "+ remainingChars;
    }
}
