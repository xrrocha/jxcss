package jxcss;

import org.w3c.css.sac.Parser;

/**
 * SteadyStateCSSParserFactory.
 */
public class SteadyStateCSSParserFactory implements CSSParserFactory {
    public Parser newParser() {
        return new com.steadystate.css.parser.SACParser();
    }
}
