package jxcss;

import java.io.Reader;


import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.Parser;
import org.xml.sax.SAXException;

import plenix.components.xml.sax.pipeline.generator.AbstractSAXGenerator;
import plenix.components.xml.sax.pipeline.generator.SAXGenerator;
import plenix.components.xml.sax.pipeline.generator.SAXGeneratorFactory;



/**
 * CSSParserSAXGeneratorFactory.
 */
public class CSSParserSAXGeneratorFactory implements SAXGeneratorFactory {
    private String namespacePrefix = "";
    private CSSParserFactory parserFactory;
    
    public CSSParserSAXGeneratorFactory(){}
    public CSSParserSAXGeneratorFactory(CSSParserFactory parserFactory) {
        setParserFactory(parserFactory);
    }
    public CSSParserSAXGeneratorFactory(String namespacePrefix, CSSParserFactory parserFactory) {
        setNamespacePrefix(namespacePrefix);
        setParserFactory(parserFactory);
    }
    
    public SAXGenerator newInstance() throws SAXException {
        return new AbstractSAXGenerator() {
            public void generateFrom(Object source) throws SAXException {
                try {
                    Parser parser = getParserFactory().newParser();
                    SAXCSSDocumentHandler documentHandler = new SAXCSSDocumentHandler(getContentHandler());
                    documentHandler.setNamespacePrefix(getNamespacePrefix());
                    parser.setDocumentHandler(documentHandler);
                    parser.parseStyleSheet(new InputSource((Reader) source));
                } catch (Exception e) {
                    throw new SAXException(e);
                }
            }
        };
    }

    /**
     * @param parserFactory The parserFactory to set.
     */
    public void setParserFactory(CSSParserFactory parserFactory) {
        this.parserFactory = parserFactory;
    }

    /**
     * @return Returns the parserFactory.
     */
    public CSSParserFactory getParserFactory() {
        return parserFactory;
    }
    /**
     * @param namespacePrefix The namespacePrefix to set.
     */
    public void setNamespacePrefix(String namespacePrefix) {
        this.namespacePrefix = namespacePrefix;
    }
    /**
     * @return Returns the namespacePrefix.
     */
    public String getNamespacePrefix() {
        return namespacePrefix;
    }
}
