package jxcss;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import plenix.components.processor.pipeline.Generator;
import plenix.components.processor.pipeline.PipelineProcessor;
import plenix.components.processor.pipeline.Serializer;
import plenix.components.processor.pipeline.sax.serializer.ApacheXMLSerializer;
import plenix.components.processor.pipeline.sax.transformer.TraxTransformer;

/**
 * This class provides the command-line interface for JXCSS.
        <p>
            The command-line incantation is:
        </p>
        <pre class="code">
            java  jxcss.Main  [-c]  [-p ss|flute|batik]  [- | &lt;inputURL&gt;]  [- | &lt;outputFile&gt;]
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
                        <code>ss</code>: Use the Steady State CSS parser. This is the
                        current default.
                    </li>
                    <li>
                        <code>flute</code>: Use the W3C Flute parser.
                    </li>
                    <li>
                        <code>batik</code>: Use the Apache Batik parser.
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
    public static void main(String[] args) {
        try {
            boolean compact = false;
            String parserName = "ss";
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
            CSSParserFactory parserFactory = null;
            if ("ss".equals(parserName)) {
                parserFactory = new SteadyStateCSSParserFactory();
            } else if ("flute".equals(parserName)) {
                parserFactory = new FluteCSSParserFactory();
            } else if ("batik".equals(parserName)) {
                parserFactory = new BatikCSSParserFactory();
            } else {
                error("Invalid parser name: " + parserName);
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

            Generator generator = new CSSParserGenerator(parserFactory, SAXCSSDocumentHandler.DEFAULT_NAMESPACE_PREFIX);
            List transformers = new ArrayList();
            if (compact) {
                InputStream sis = Main.class.getResourceAsStream("compact-xcss.xsl");
                transformers.add(new TraxTransformer(new StreamSource(sis)));
            }
            Serializer serializer = new ApacheXMLSerializer();
            PipelineProcessor pipeline = new PipelineProcessor(generator, transformers, serializer);
            
            pipeline.process(new InputStreamReader(in), new OutputStreamWriter(out), null);
        } catch (Exception e) {
            e.printStackTrace();
            error("Error during pipeline processing", e);
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
        System.err.println("Usage: jxcss.Main [-c] [-p ss|flute|batik] [input-url] [output-file]");
        System.exit(1);
    }
}
