package org.plenix.jxcss;

import java.util.List;

public class JXCSSException extends RuntimeException {
    public static final long serialVersionUID = 1L;

    public final static String LINE_SEPARATOR = System.getProperty("line.separator");

    public JXCSSException(String message) {
        super(message);
    }

    public JXCSSException(String message, Throwable cause) {
        super(message, cause);
    }

    public static String buildMessage(String format, Object... args) {
        return String.format(format, args);
    }

    public static String joinMessages(List<String> errorMessages) {
        StringBuilder sb = new StringBuilder();
        for (String errorMessage : errorMessages) {
            sb.append(LINE_SEPARATOR);
            sb.append("\t- ");
            sb.append(errorMessage);
        }
        return sb.toString();
    }
}
