package jxcss;

import java.io.IOException;
import java.io.Reader;

import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.Parser;

import plenix.components.processor.pipeline.sax.generator.SAXGenerator;

/**
 * CSSParserGenerator.
 */
public class CSSParserGenerator extends SAXGenerator {
    private String namespacePrefix = "";
    private CSSParserFactory parserFactory;
    
    public CSSParserGenerator() {}
    
    public CSSParserGenerator(CSSParserFactory parserFactory) {
        setParserFactory(parserFactory);
    }
    
    public CSSParserGenerator(CSSParserFactory parserFactory, String namespacePrefix) {
        setParserFactory(parserFactory);
        setNamespacePrefix(namespacePrefix);
    }
    
    /**
     * @see plenix.components.processor.pipeline.Generator#generateFrom(java.lang.Object)
     */
    public void generateFrom(Object source) throws IOException {
        Parser parser = getParserFactory().newParser();
        SAXCSSDocumentHandler documentHandler = new SAXCSSDocumentHandler(getContentHandler(), getNamespacePrefix());
        parser.setDocumentHandler(documentHandler);
        parser.parseStyleSheet(new InputSource((Reader) source));
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
