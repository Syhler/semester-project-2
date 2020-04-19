package org.example.domain;

import org.example.entity.CompanyEntity;
import org.example.entity.CreditEntity;
import org.example.entity.ProgramEntity;
import org.example.entity.UserEntity;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Export
{

    /**
     * export the given programEntity to an xml file
     * @param programEntity program you want to export
     * @param path the path where the file will be saved IMPORTANT the filename and extension
     *             needs to be included in the path example: ../name.xml
     * @return the file there was created
     */
    public static File program(ProgramEntity programEntity, String path)
    {

        if (programEntity == null) return null;

        var document = createNewDocument();

        if (document == null) return null;


        Element root = document.createElement("epg");
        document.appendChild(root);

        Element programList = document.createElement("programlist");
        root.appendChild(programList);


        addProgramToFile(programList, document, programEntity);


        return createFile(document, path);
    }

    /**
     * export the given list of programEntity to an xml file
     * @param programEntityList List of programs you want to export
     * @param path the path where the file will be saved IMPORTANT the filename and extension
     *             needs to be included in the path example: ../name.xml
     * @return the filed there was created
     */
    public static File program(List<ProgramEntity> programEntityList, String path)
    {

        if (programEntityList == null) return null;

        var document = createNewDocument();

        if (document == null) return null;

        Element root = document.createElement("epg");
        document.appendChild(root);

        Element programList = document.createElement("programlist");
        root.appendChild(programList);

        for (var program : programEntityList)
        {
            addProgramToFile(programList, document, program);
        }

        return createFile(document, path);
    }

    private static File createFile(Document document, String path)
    {
        File file = null;

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            file = new File(path);
            StreamResult streamResult = new StreamResult(file);
            transformer.transform(domSource, streamResult);

        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return file;
    }

    private static void addProgramToFile(Element root, Document document, ProgramEntity programEntity)
    {

        // program element
        Element program = document.createElement("program");
        root.appendChild(program);

        Element credits = document.createElement("credits");
        program.appendChild(credits);

        Element roles = document.createElement("roles");
        program.appendChild(roles);

        //set attributes
        Attr id = document.createAttribute("id");
        id.setValue(String.valueOf(programEntity.getId()));
        program.setAttributeNode(id);

        Attr title = document.createAttribute("title");
        title.setValue(programEntity.getName());
        program.setAttributeNode(title);

        Attr description = document.createAttribute("description");
        description.setValue(programEntity.getName());
        program.setAttributeNode(description);

        List<CompanyEntity> companyEntities = new ArrayList<>();
        companyEntities.add(programEntity.getCompany());

        addProducerToFile(roles, document, programEntity.getProducer());
        addCompaniesToFile(roles, document, companyEntities);
        addCreditsToFile(credits, document, programEntity.getCredits());
    }


    private static void addCreditsToFile(Element root, Document document, List<CreditEntity> credits)
    {
        if (credits == null) return;

        for (var credit: credits) {

            Element creditElement = document.createElement("credit");
            root.appendChild(creditElement);

            addUserToFile(creditElement, document, credit.getActor());


        }
    }


    private static void addUserToFile(Element root, Document document, UserEntity userEntity)
    {
        Attr firstName = document.createAttribute("firstName");
        firstName.setValue(userEntity.getFirstName());
        root.setAttributeNode(firstName);

        Attr middleName = document.createAttribute("middleName");
        middleName.setValue(userEntity.getMiddleName());
        root.setAttributeNode(middleName);

        Attr lastName = document.createAttribute("lastName");
        lastName.setValue(userEntity.getLastName());
        root.setAttributeNode(lastName);

        Attr title = document.createAttribute("title");
        title.setValue(userEntity.getTitle());
        root.setAttributeNode(title);

        Attr email = document.createAttribute("email");
        email.setValue(userEntity.getEmail());
        root.setAttributeNode(email);

        //set root for company
        Element companyElement = document.createElement("company");
        root.appendChild(companyElement);

        addCompanyToFile(companyElement, document, userEntity.getCompany());
    }


    private static void addCompaniesToFile(Element root, Document document, List<CompanyEntity> companies)
    {
        if (companies == null) return;

        for (var company: companies)
        {
            Element roleElement = document.createElement("role");
            root.appendChild(roleElement);

            Attr function = document.createAttribute("function");
            function.setValue("Producent");
            roleElement.setAttributeNode(function);

            addCompanyToFile(roleElement, document, company);
        }

    }

    private static void addCompanyToFile(Element root, Document document, CompanyEntity companyEntity)
    {
        if (companyEntity == null) return;

        Attr name = document.createAttribute("name");
        name.setValue(companyEntity.getName());
        root.setAttributeNode(name);

    }


    /**
     *
     * @param root the element you want to add the producers to
     * @param document the xml "file"
     * @param producers the producers you want to add
     */
    private static void addProducerToFile(Element root, Document document, List<UserEntity> producers)
    {

        if (producers == null) return;

        for (var producer : producers)
        {
            Element roleElement = document.createElement("role");
            root.appendChild(roleElement);

            Attr function = document.createAttribute("function");
            function.setValue("Instruktør");
            roleElement.setAttributeNode(function);

            addUserToFile(roleElement, document, producer);
        }

    }

    /**
     * create a new document
     * @return the new document
     */
    private static Document createNewDocument()
    {

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            return documentBuilder.newDocument();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }

    }



}
