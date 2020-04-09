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
import java.util.List;

public class Export
{

    public File program(ProgramEntity programEntity, String path)
    {

        if (programEntity == null) return null;

        var document = createNewDocument();

        if (document == null) return null;


        Element root = document.createElement("programlist");
        document.appendChild(root);


        addProgramToFile(root, document, programEntity);


        return createFile(document, path);
    }

    public File program(List<ProgramEntity> programEntityList, String path)
    {

        if (programEntityList == null) return null;

        var document = createNewDocument();

        if (document == null) return null;


        Element root = document.createElement("programlist");
        document.appendChild(root);

        for (var program : programEntityList)
        {
            addProgramToFile(root, document, program);
        }

        return createFile(document, path);
    }


    private File createFile(Document document, String path)
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

    private void addProgramToFile(Element root, Document document, ProgramEntity programEntity)
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
        id.setValue(programEntity.getId());
        program.setAttributeNode(id);

        Attr title = document.createAttribute("title");
        title.setValue(programEntity.getName());
        program.setAttributeNode(title);

        Attr description = document.createAttribute("description");
        description.setValue(programEntity.getName());
        program.setAttributeNode(description);

        addProducerToFile(roles, document, programEntity.getProducer());
        addCompaniesToFile(roles, document, programEntity.getCompanies());
        addCreditsToFile(credits, document, programEntity.getCredits());
    }


    private void addCreditsToFile(Element root, Document document, List<CreditEntity> credits)
    {
        if (credits == null) return;

        for (var credit: credits) {

            Element creditElement = document.createElement("credit");
            root.appendChild(creditElement);

            addUserToFile(creditElement, document, credit.getActor());


        }
    }


    private void addUserToFile(Element root, Document document, UserEntity userEntity)
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


    private void addCompaniesToFile(Element root, Document document, List<CompanyEntity> companies)
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

    private void addCompanyToFile(Element root, Document document, CompanyEntity companyEntity)
    {

        if (companyEntity == null) return;

        Attr name = document.createAttribute("name");
        name.setValue(companyEntity.getName());
        root.setAttributeNode(name);

    }


    private void addProducerToFile(Element root, Document document, List<UserEntity> producers)
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
    private Document createNewDocument()
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
