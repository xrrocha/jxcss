package jxcss;

import org.w3c.css.sac.Parser;

/**
 * This class implements the {@link CSSParserFactory} interface for the
 * <a href="http://xml.apache.org/batik/" target="_top">Batik</a> CSS parser.
 */
public class BatikCSSParserFactory implements CSSParserFactory {
    /**
     * Create a new instance of <code>org.apache.batik.css.parser.Parser</code>.
     * 
     * @return The {@link CSSParserFactory} instance. 
     */
    public Parser newParser() {
        return new org.apache.batik.css.parser.Parser();
    }
}
