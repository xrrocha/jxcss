package jxcss;

import java.io.Reader;

import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.Parser;
import org.xml.sax.SAXException;

import plenix.components.processor.pipeline.Generator;
import plenix.components.processor.pipeline.GeneratorFactory;
import plenix.components.processor.pipeline.sax.generator.SAXGenerator;


/**
 * CSSParserSAXGeneratorFactory.
 */
public class CSSParserGeneratorFactory implements GeneratorFactory {
    private String namespacePrefix = "";
    private CSSParserFactory parserFactory;
    
    public CSSParserGeneratorFactory(){}
    public CSSParserGeneratorFactory(CSSParserFactory parserFactory) {
        setParserFactory(parserFactory);
    }
    public CSSParserGeneratorFactory(String namespacePrefix, CSSParserFactory parserFactory) {
        setNamespacePrefix(namespacePrefix);
        setParserFactory(parserFactory);
    }
    
    public Generator newGenerator() {
        return new SAXGenerator() {
            public void generateFrom(Object reader) throws Exception {
                try {
                    Parser parser = getParserFactory().newParser();
                    SAXCSSDocumentHandler documentHandler = new SAXCSSDocumentHandler(getContentHandler());
                    documentHandler.setNamespacePrefix(getNamespacePrefix());
                    parser.setDocumentHandler(documentHandler);
                    parser.parseStyleSheet(new InputSource((Reader) reader));
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
