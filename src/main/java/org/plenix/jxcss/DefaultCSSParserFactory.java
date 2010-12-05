package org.plenix.jxcss;

import java.util.Map;

import org.w3c.css.sac.Parser;

public class DefaultCSSParserFactory implements CSSParserFactory {
    private String preferredParserName = "batik";
    private Map<String, String> parserClasses;
    
    public Parser newParser() {
        return newParserFor(preferredParserName);
    }

	@Override
    public Parser newParserFor(String parserName) {
	    if (parserClasses == null || !parserClasses.containsKey(parserName)) {
	        throw new IllegalArgumentException("No such parser: " + parserName);
	    }
	    
	    String parserClassName = parserClasses.get(parserName);
	    if (parserClassName == null) {
	        throw new IllegalStateException("No class configured for " + parserName);
	    }
	    
        try {
            return (Parser) classFromName(parserClassName).newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Error instantiating parser " + parserClassName);
        }
    }
    
    public  Class<?> classFromName(String className) {
        try {
            return getClass().getClassLoader().loadClass(className);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error loading class '%1$s' by name", className), e);
        }
    }

    public String getPreferredParserName() {
		return preferredParserName;
	}

	public void setPreferredParserName(String preferredParserName) {
		this.preferredParserName = preferredParserName;
	}

    public Map<String, String> getParserClasses() {
        return parserClasses;
    }

    public void setParserClasses(Map<String, String> parserClasses) {
        this.parserClasses = parserClasses;
    }
}
