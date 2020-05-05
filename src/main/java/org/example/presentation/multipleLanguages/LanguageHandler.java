package org.example.presentation.multipleLanguages;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.example.App;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LanguageHandler
{

    private static Language language;
    private static HashMap<String, String> header;
    private static HashMap<String, String> defaultHeader;


    public static Language getLanguage() {
        return language;
    }

    public static String getText(String key)
    {
        var text = LanguageHandler.header.get(key.toLowerCase());
        if (text == null || text.isEmpty() || text.isBlank())
        {
            text = LanguageHandler.defaultHeader.get(key.toLowerCase());
        }

        return text;
    }

    /**
     * Loads the given language
     * @param language What language to load
     */
    public static void initLanguage(Language language)
    {
        LanguageHandler.language = language;

        var file = getLanguageXml();

        if (file != null)
        {
            LanguageHandler.header = createLanguageHeader(file);
            LanguageHandler.defaultHeader = createLanguageHeader(new File(App.class.getResource("languages/english.xml").getPath()));
        }
    }

    /**
     * Creates a haspMap based on the given XML file.
     * @param file XML File
     * @return a hashMap containing the xml file attributes
     */
    private static HashMap<String, String> createLanguageHeader(File file)
    {
        var tempHeader = new HashMap<String, String>();

        try {

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            //creates the document from xml file
            Document doc = documentBuilder.parse(file);

            //normalize the data -> https://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work For explanation
            //on why to normalize the data first
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++)
            {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) node;

                    var key = element.getTagName().toLowerCase();
                    var value = element.getTextContent();

                    tempHeader.put(key,value);

                }
            }

        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }


        return tempHeader;
    }

    /**
     * Finds a xml file based on the chosen language
     * @return XML file
     */
    private static File getLanguageXml()
    {
        switch (LanguageHandler.language)
        {
            case Danish:
                var danishPath = App.class.getResource("languages/danish.xml").getPath();
                return new File(danishPath);
            case English:
                var englishPath = App.class.getResource("languages/english.xml").getPath();
                return new File(englishPath);
            case Russian:
                var russianPath = App.class.getResource("languages/russian.xml").getPath();
                return new File(russianPath);
            case Swedish:
                var swedishPath = App.class.getResource("languages/Swedish.xml").getPath();
                return new File(swedishPath);
            case Norwegian:
                var norwegianPath = App.class.getResource("languages/norwegian.xml").getPath();
                return new File(norwegianPath);
        }

        return null;

    }


}
