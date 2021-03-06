package org.plenix.jxcss;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.css.sac.DocumentHandler;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.Parser;
import org.xml.sax.ContentHandler;
import org.yaml.snakeyaml.Yaml;

/**
 * This class provides the command-line interface for JXCSS.
        <p>
            The command-line incantation is:
        </p>
        <pre class="code">
            java  org.plenix.jxcss.Main  [-c]  [-p batik|steadyState|flute]  [- | &lt;inputURL&gt;]  [- | &lt;outputFile&gt;]
        </pre>
        <p>
            where:
        </p>
        <ul>
            <li>
                The optional <code>-c</code> option instructs JXCSS to produce output in the
                <i>compact</i> syntax. The compact syntax exposes a less verbose
                (albeit less concise) structure of the stylesheet.
            </li>
            <li>
                The optional <code>-p</code> option specifies what SAC parser to use.
                Recognized values are:
                <ul>
                    <li>
                        <code>batik</code>: Use the Apache Batik parser. This is the
                        current default.
                    </li>
                    <li>
                        <code>steadyState</code>: Use the Steady State CSS parser.
                    </li>
                    <li>
                        <code>flute</code>: Use the W3C Flute parser.
                    </li>
                </ul>
            </li>
            <li>
                The optional first argument names the input stylesheet URL. This can be:
                <ul>
                    <li>
                        A fully qualified URL such as
                        <code>http://localhost:8080/css/default.css</code> or
                        <code>file:///var/www/htdocs/css/default.css</code>.
                    </li>
                    <li>
                        Un unqualified URL such as
                        <code>default.css</code> or
                        <code>c:\docs\default.css</code>.
                        Unqualified URL's are interpreted as local file names.
                    </li>
                    <li>
                        A dash (<code>-</code>) which stands for the operating system
                        standard input.
                    </li>
                </ul>
                When omitted, the first argument defaults to the operating system's
                standard input.
            </li>
            <li>
                The optional second argument names a local file where the output XML is
                to be stored. If omitted or specified as a dash (<code>-</code>),
                output is sent to the operating system's standard output.
            </li>
        </ul>
 */
public class Main {
    /**
     * Command-line interface for JXCSS.
     * 
     * @param args The command-line arguments (explained above)
     */
    public static void main(String... args) {
        try {
            boolean compact = false;
            String parserName = null;
            InputStream in = System.in;
            OutputStream out = System.out;

            int idx = 0;
            if (idx < args.length) {
                if ("-c".equals(args[idx])) {
                    idx++;
                    compact = true;
                }
            }
            if (idx < args.length) {
                if ("-p".equals(args[idx])) {
                    if (++idx < args.length) {
                        parserName = args[idx++];
                    } else {
                        error("-p option requires an argument");
                    }
                }
            }
            if (idx < args.length) {
                try {
                    String url = args[idx++];
                    if (!"-".equals(url)) {
                        if (url.indexOf("://") != -1) {
                            in = new URL(url).openStream();
                        } else {
                            in = new FileInputStream(url);
                        }
                    }
                } catch (Exception e) {
                    error("Error opening input URL", e);
                }
            }
            if (idx < args.length) {
                try {
                    if (!"-".equals(args[idx])) {
                        out = new FileOutputStream(args[idx]);
                    }
                } catch (Exception e) {
                    error("Error opening output file", e);
                }
            }
            
            ClassLoader classLoader = Main.class.getClassLoader();
            
            Yaml yaml = new Yaml();
            InputStream configurationIS = classLoader.getResourceAsStream("jxcss-configuration.yaml");
            CSSParserFactory factory = (CSSParserFactory) yaml.load(configurationIS);

            Parser cssParser = null;
            if (parserName == null) {
            	cssParser = factory.newParser();
            } else {
            	cssParser = factory.newParserFor(parserName);
            }
            
            SAXTransformerFactory transformerFactory = (SAXTransformerFactory) TransformerFactory.newInstance();
            TransformerHandler transformerHandler = transformerFactory.newTransformerHandler();
            
            Result result = new StreamResult(out);
            transformerHandler.setResult(result);
            
            Transformer transformer = transformerHandler.getTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            ContentHandler saxContentHandler = transformerHandler;
            if (compact) {
                InputStream is = classLoader.getResourceAsStream("compact-xcss.xsl");
                TransformerHandler compactStylesheetHandler = transformerFactory.newTransformerHandler(new StreamSource(is));
                compactStylesheetHandler.setResult(new SAXResult(transformerHandler));
                saxContentHandler = compactStylesheetHandler;
            }
            
            DocumentHandler cssDocumentHandler = new SAXCSSDocumentHandler(saxContentHandler);
            cssParser.setDocumentHandler(cssDocumentHandler);
            cssParser.parseStyleSheet(new InputSource(new InputStreamReader(in)));
        } catch (Exception e) {
            e.printStackTrace();
            error("Error during CSS transformation", e);
        }
    }

    private static void error(String message) {
        error(message, null);
    }

    private static void error(String message, Exception exception) {
        System.err.print(message);
        if (exception != null) {
            String exceptionMessage = exception.getMessage();
            if (exceptionMessage == null) {
                exceptionMessage = exception.toString();
            }
            System.err.print(": " + exceptionMessage);
        }
        System.err.println();
        usage();
    }

    private static void usage() {
        System.err.println("Usage: org.plenix.jxcss.Main [-c] [-p steadyState|flute|batik] [input-url] [output-file]");
        System.exit(1);
    }
}
