package org.example.domain.io;

import org.example.domain.buisnessComponents.Company;
import org.example.domain.buisnessComponents.Program;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Import
{

    /**
     * loads program from an xml file
     * @param file the file to retrieve programs from
     * @return a list of programEntity objects.
     */
    public static List<Program> loadPrograms(File file)
    {

        List<Program> programEntites = new ArrayList<>();

        if (!isXMLFile(file)) return programEntites;


        try {

            var document = createDocument(file);

            NodeList programs = getProgramNodesFromXml(document);

            if (programs != null)
            {
                programEntites.addAll(getProgramsFromNodeList(programs));
            }


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return programEntites;
    }


    /**
     * Retrieve all programs from the given nodeList
     * @param programs the nodelist of programs
     * @return a list of programEntity objects.
     */
    private static List<Program> getProgramsFromNodeList(NodeList programs)
    {
        var programEntities = new ArrayList<Program>();

        for (int i = 0; i < programs.getLength(); i++)
        {
            Node node = programs.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                var program = createProgram(node);

                programEntities.add(program);
            }
        }

        return programEntities;
    }

    /**
     * Finds all the programs inside the given document.
     * @param document the document created from a xml file
     * @return a nodeList that contains programs.
     */
    private static NodeList getProgramNodesFromXml(Document document)
    {
        Element root = document.getDocumentElement();

        NodeList programList = root.getElementsByTagName("programlist");

        if (programList.getLength() > 0)
        {
            return programList.item(0).getChildNodes();
        }

        return null;
    }

    /**
     * creates a Document you can interact with from a xml file
     * @param file the xml file you want to interact with
     * @return a Document reference
     */
    private static Document createDocument(File file) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(file);

        document.getDocumentElement().normalize();

        return document;
    }

    /**
     * creates a programEntity object from given node
     * @return a programEntity object
     */
    private static Program createProgram(Node node)
    {
        Element element = (Element) node;

        var title = element.getAttribute("title").trim();
        var titleOriginal = element.getAttribute("title_original").trim();
        var description = element.getAttribute("description").trim();
        Company company = null;
        var companies = getCompanies(element);
        //Add producer, use "getCompanies" method as reference
        if (companies.size() > 0)
        {
            //redo this if we allow multiple companies
            company = companies.get(0);
        }


        if (title.equals(titleOriginal)) titleOriginal = "";

        return new Program(
                title + " " + titleOriginal,
                description,
                company
        );
    }


    /**
     * Creates a list of companies from given element.
     * @param element from where to find roles
     * @return list of companyEntites
     */
    private static List<Company> getCompanies(Element element)
    {
        List<Company> companyEntities = new ArrayList<>();

        var roles = element.getElementsByTagName("roles").item(0).getChildNodes();

        for (int i = 0; i < roles.getLength(); i++)
        {
            Node node = roles.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                if (((Element)node).getAttribute("function").equals("Instruktør")) continue;

                var company = createCompany(node);

                if (company != null)
                {
                    companyEntities.add(company);
                }
            }
        }

        return companyEntities;
    }


    /**
     * creates a companyEntity object from a node
     * @return a companyEntity object
     */
    private static Company createCompany(Node node)
    {
        Element elementNode = (Element) node;
        var companyName = elementNode.getAttribute("name");
        if (!companyName.isBlank())
        {
            return new Company(companyName.trim());
        }

        return null;
    }

    /**
     * Checks if the given file is of type XML
     * @param file to check
     * @return true if the file is of type XML
     */
    private static boolean isXMLFile(File file)
    {
        var extension = getExtensionByStringHandling(file.getName());

        return extension.map(s -> s.equals("xml")).orElse(false);

    }

    /**
     * Gets the extension from a filename.
     * @param filename the name to retrieve the extension from
     * @return the extension
     */
    public static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
