package jxcss;

import org.w3c.css.sac.Parser;

/**
 * This class implements the {@link CSSParserFactory} interface for the
 * <a href="http://www.w3.org/Style/CSS/SAC/" target="_top">Batik</a> CSS parser.
 */
public class FluteCSSParserFactory implements CSSParserFactory {
    /**
     * Create a new instance of <code>org.w3c.flute.parser.Parser</code>.
     * 
     * @return The {@link CSSParserFactory} instance. 
     */
    public Parser newParser() {
        return new org.w3c.flute.parser.Parser();
    }
}
