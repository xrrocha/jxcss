package jxcss;

import org.w3c.css.sac.Parser;

/**
 * This class implements the {@link CSSParserFactory} interface for the
 * <a href="http://cssparser.sourceforge.net/" target="_top">Steady State</a> CSS parser.
 */
public class SteadyStateCSSParserFactory implements CSSParserFactory {
    /**
     * Create a new instance of <code>com.steadystate.css.parser.SACParser</code>.
     * 
     * @return The {@link CSSParserFactory} instance. 
     */
    public Parser newParser() {
        return new com.steadystate.css.parser.SACParser();
    }
}
