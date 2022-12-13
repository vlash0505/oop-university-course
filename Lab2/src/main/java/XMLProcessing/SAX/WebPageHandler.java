package XMLProcessing.SAX;

import WebPage.WebPage;
import WebPage.WebPageType;
import WebPage.WebPageFactory;
import WebPage.WebPageXmlTag;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class WebPageHandler extends DefaultHandler {
    private final Set<WebPage> webPages;
    private WebPage current;
    private WebPageXmlTag currentXmlTag;
    private Attributes currentAttrs;
    private final EnumSet<WebPageXmlTag> withText;
    private static final String ELEMENT_WEB_PAGE = "page";

    public WebPageHandler() {
        webPages = new HashSet<>();
        withText = EnumSet.range(WebPageXmlTag.PAGE, WebPageXmlTag.POLL);
    }

    public Set<WebPage> getWebPages() {
        return webPages;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if (ELEMENT_WEB_PAGE.equals(qName)) {
            WebPageType currentPageType = WebPageType.valueOf(attrs.getValue(1).toUpperCase());
            current = WebPageFactory.getInstance(currentPageType);
            current.setNeedsAuthorization(Boolean.parseBoolean(attrs.getValue(2)));
            current.setId(Integer.parseInt(attrs.getValue(0).replace("ID-", "")));
        } else {
            WebPageXmlTag temp = WebPageXmlTag.valueOf(qName.toUpperCase());
            if (withText.contains(temp)) {
                currentXmlTag = temp;
                currentAttrs = attrs;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (ELEMENT_WEB_PAGE.equals(qName)) {
            webPages.add(current);
        }
    }

    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).strip();
        if (currentXmlTag != null) {
            switch (currentXmlTag) {
                case TITLE -> current.setTitle(data);
                case POLL -> current.setArePollsAvailable(true);
                case AUTHORIZATION -> current.setArePollsNeedAuthorization(Boolean.parseBoolean(data));
                case CHARS -> {
                    String mail = currentAttrs.getValue("mail");
                    String news = currentAttrs.getValue("news");
                    String paid = currentAttrs.getValue("paid");
                    String archive = currentAttrs.getValue("archive");

                    if (mail != null) {
                        current.setMailAvailable(Boolean.parseBoolean(mail));
                    }

                    if (news != null) {
                        current.setNewsAvailable(Boolean.parseBoolean(news));
                    }

                    if (paid != null) {
                        current.setPaid(Boolean.parseBoolean(paid));
                    }

                    if (archive != null) {
                        current.setArchivesAvailable(Boolean.parseBoolean(archive));
                    }
                }
            }
        }
        currentXmlTag = null;
    }
}
