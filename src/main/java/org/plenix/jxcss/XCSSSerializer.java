package org.plenix.jxcss;

import java.io.PrintWriter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XCSSSerializer extends DefaultHandler {
    private PrintWriter out;
    private String namespaceUri = SAXCSSDocumentHandler.XCSS_NAMESPACE_URI;
    
    public XCSSSerializer() {}
    
    public XCSSSerializer(PrintWriter out) {
        setOut(out);
    }
    
    public XCSSSerializer(PrintWriter out, String namespaceUri) {
        setOut(out);
        setNamespaceUri(namespaceUri);
    }

    public void startDocument() throws SAXException {
        if (out == null) {
            throw new IllegalStateException("Printer has not been set");
        }
    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException {
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (namespaceUri.equals(uri)) {
            
        }
    }
    
    public void characters(char[] ch, int start, int length) throws SAXException {
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
    }

    public void endPrefixMapping(String prefix) throws SAXException {
    }

    public void endDocument() throws SAXException {
        out.flush();
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        if (out == null) {
            throw new NullPointerException("Printer cannot be null");
        }
        this.out = out;
    }

    public String getNamespaceUri() {
        return namespaceUri;
    }

    public void setNamespaceUri(String namespaceUri) {
        if (namespaceUri == null) {
            throw new NullPointerException("Namespace URI cannot be null");
        }
        this.namespaceUri = namespaceUri;
    }
}
