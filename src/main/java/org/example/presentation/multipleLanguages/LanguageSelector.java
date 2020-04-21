package org.example.presentation.multipleLanguages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.domain.DomainFacade;
import org.example.presentation.DefaultController;



import java.util.ArrayList;
import java.util.List;

public class LanguageSelector extends ListCell<LanguageModel> {
    private List<Language> language;

    @Override
    public void updateItem(LanguageModel item, boolean empty){
        super.updateItem(item, empty);
        setGraphic(null);
        setText(null);
        if(item!=null){
            ImageView imageView = new ImageView(item.getImage());
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            setGraphic(imageView);
            setText(item.getLanguage().toString());
        }
    }
}
