package jxcss;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.xml.transform.stream.StreamSource;

import plenix.components.xml.sax.pipeline.SAXPipeline;
import plenix.components.xml.sax.pipeline.SAXPipelineFactory;
import plenix.components.xml.sax.pipeline.generator.CSSParserSAXGeneratorFactory;
import plenix.components.xml.sax.pipeline.serializer.ApacheXMLSerializerFactory;
import plenix.components.xml.sax.pipeline.transformer.TraxSAXTransformerFactory;

/**
 * Main.
 */
public class Main {
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
                    if (url.indexOf("://") == -1) {
                        url = "file:///" + new File(url).getAbsolutePath();
                    }
                    in = new URL(url).openStream();
                } catch (Exception e) {
                    error("Error opening input URL", e);
                }
            }
            if (idx < args.length) {
                try {
                    out = new FileOutputStream(args[idx]);
                } catch (Exception e) {
                    error("Error opening output file", e);
                }
            }

            SAXPipelineFactory pipelineFactory = new SAXPipelineFactory();
            pipelineFactory.setGeneratorFactory(new CSSParserSAXGeneratorFactory(
                    SAXCSSDocumentHandler.DEFAULT_NAMESPACE_PREFIX, parserFactory));
            if (compact) {
                InputStream sis = Main.class.getResourceAsStream("compact-xcss.xsl");
                pipelineFactory.addTransformerFactory(new TraxSAXTransformerFactory(new StreamSource(sis)));
            }
            pipelineFactory.setSerializerFactory(new ApacheXMLSerializerFactory());
            SAXPipeline pipeline = pipelineFactory.newPipeline();
            pipeline.process(new InputStreamReader(in), new OutputStreamWriter(out));
        } catch (Exception e) {
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
