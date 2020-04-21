package org.example.presentation.multipleLanguages;

import javafx.scene.image.Image;

public class LanguageModel {
    private Image image;
    private Language language;

    public LanguageModel(Image image, Language language)
    {
        setImage(image);
        setLanguage(language);
    }

    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    public Language getLanguage()
    {
        return language;
    }

    public void setLanguage(Language language)
    {
        this.language = language;
    }

}
