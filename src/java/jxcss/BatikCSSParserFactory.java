package jxcss;

import org.w3c.css.sac.Parser;

/**
 * BatikCSSParserFactory.
 */
public class BatikCSSParserFactory implements CSSParserFactory {
    public Parser newParser() {
        return new org.apache.batik.css.parser.Parser();
    }
}
