package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.presentation.DefaultController;
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



        LanguageHandler.initLanguage(Language.English);

        scene = new Scene(getLoader("default").load());
        stage.setMinWidth(1280);
        stage.setMinHeight(720);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("loginImages/tv2trans.png")));
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