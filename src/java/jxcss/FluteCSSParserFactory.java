package jxcss;

import org.w3c.css.sac.Parser;

/**
 * FluteCSSParserFactory.
 */
public class FluteCSSParserFactory implements CSSParserFactory {
    public Parser newParser() {
        return new org.w3c.flute.parser.Parser();
    }
}
