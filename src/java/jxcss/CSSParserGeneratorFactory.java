package jxcss;

import plenix.components.processor.pipeline.Generator;
import plenix.components.processor.pipeline.GeneratorFactory;


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
        return new CSSParserGenerator(getParserFactory(), getNamespacePrefix());
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
