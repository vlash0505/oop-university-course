package XMLProcessing;

import XMLProcessing.DOM.WebPagesDomBuilder;
import XMLProcessing.SAX.WebPagesSaxBuilder;
import XMLProcessing.StAX.WebPagesStaxBuilder;

public class WebPageBuilderFactory {
    private enum TypeParser {
        SAX, STAX, DOM
    }

    private WebPageBuilderFactory() {
    }

    public static AbstractWebPagesBuilder createWebPageBuilder(String typeParser) {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {
            case DOM -> {
                return new WebPagesDomBuilder();
            }
            case STAX -> {
                return new WebPagesStaxBuilder();
            }
            case SAX -> {
                return new WebPagesSaxBuilder();
            }
            default -> throw new EnumConstantNotPresentException(
                    type.getDeclaringClass(), type.name());
        }
    }
}
