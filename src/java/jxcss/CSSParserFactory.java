package jxcss;

import org.w3c.css.sac.Parser;

/**
 * This interface defines the basic mechanism for applications to obtain an instance
 * of {@link CSSParserFactory}.
 */
public interface CSSParserFactory {
    /**
     * Obtain a new, properly configured instance of {@link CSSParserFactory}.
     * 
     * @return The {@link CSSParserFactory} instance. 
     */
    public Parser newParser();
}
