package org.plenix.jxcss;

import org.w3c.css.sac.Parser;

public interface CSSParserFactory {
    public Parser newParser();
    public Parser newParserFor(String parserName);
}   
