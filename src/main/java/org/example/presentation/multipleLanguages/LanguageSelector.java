package org.example.presentation.multipleLanguages;

import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import java.util.List;

public class LanguageSelector extends ListCell<LanguageModel> {
    private List<Language> language;

    /**
     * Enables pictures and language to be added to a ComboBox
     * @param item
     * @param empty
     */
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
