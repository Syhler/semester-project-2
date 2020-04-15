package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.presentation.multipleLanguages.Language;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        LanguageHandler.initLanguage(Language.Danish);
        scene = new Scene(getLoader("default").load());
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(getLoader(fxml).load());
    }

    public static FXMLLoader getLoader(String fxml) throws IOException {
        return new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    }

    public static void main(String[] args)
    {
        launch();
    }
    
}