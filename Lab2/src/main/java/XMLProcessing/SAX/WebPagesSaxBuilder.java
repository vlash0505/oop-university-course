package XMLProcessing.SAX;

import WebPage.WebPage;
import XMLProcessing.AbstractWebPagesBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Set;

public class WebPagesSaxBuilder extends AbstractWebPagesBuilder {
    private Set<WebPage> pages;
    private WebPageHandler handler = new WebPageHandler();
    private XMLReader reader;

    public WebPagesSaxBuilder() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();
            reader.setErrorHandler(new WebPageErrorHandler());
            reader.setContentHandler(handler);
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace(); // log
        }
    }

    public Set<WebPage> getWebPages() {
        return pages;
    }

    public void buildSetWebPages(String filename) {
        try {
            reader.parse(filename);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
        pages = handler.getWebPages();
    }
}
