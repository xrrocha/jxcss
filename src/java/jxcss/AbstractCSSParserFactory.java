package jxcss;

import org.w3c.css.sac.Parser;

public abstract class AbstractCSSParserFactory implements CSSParserFactory {
    private String preferredParserName;
    
    public Parser newParser() {
        return newParserFor(preferredParserName);
    }
}
