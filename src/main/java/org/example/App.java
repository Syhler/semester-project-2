package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.entity.CompanyEntity;
import org.example.entity.CreditEntity;
import org.example.entity.ProgramEntity;
import org.example.entity.UserEntity;
import org.example.presentation.multipleLanguages.Language;
import org.example.presentation.multipleLanguages.LanguageHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(getLoader(fxml).load());
    }

    public static FXMLLoader getLoader(String fxml) throws IOException {
        return new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    }

    public static void main(String[] args)
    {
        launch();
    }

    /**
     * DELETE BEFORE MIGRATE TODO(DELETE BEFORE MIGRATE)
     * DELETE BEFORE MIGRATE TODO(DELETE BEFORE MIGRATE)
     * DELETE BEFORE MIGRATE TODO(DELETE BEFORE MIGRATE)
     * DELETE BEFORE MIGRATE TODO(DELETE BEFORE MIGRATE)
     */
    public static List<ProgramEntity> dummyData()
    {
        var company = new CompanyEntity("DummyCompany");
        var user = new UserEntity("Lydmand", "Søren", "frends","bjarke", null, "bjarke@sd.com");

        user.setCompany(company);

        var credit = new CreditEntity("", user);


        var listCompany = new ArrayList<CompanyEntity>();
        var listUser = new ArrayList<UserEntity>();
        var listCredit = new ArrayList<CreditEntity>();
        listCompany.add(company);
        listUser.add(user);
        listCredit.add(credit);
        listCredit.add(credit);
        listCredit.add(credit);
        listCredit.add(credit);
        listCredit.add(credit);



        var program = new ProgramEntity("Fedt mand", "fed description", listCompany, listUser,listCredit);


        program.setId("1000");

        var list = new ArrayList<ProgramEntity>();

        list.add(program);
        list.add(program);
        list.add(program);
        list.add(program);
        list.add(program);
        list.add(program);
        list.add(program);

        return list;

    }

}