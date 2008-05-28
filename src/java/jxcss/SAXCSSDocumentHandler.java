package jxcss;

import org.w3c.css.sac.AttributeCondition;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CharacterDataSelector;
import org.w3c.css.sac.CombinatorCondition;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.ConditionalSelector;
import org.w3c.css.sac.ContentCondition;
import org.w3c.css.sac.DescendantSelector;
import org.w3c.css.sac.DocumentHandler;
import org.w3c.css.sac.ElementSelector;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.LangCondition;
import org.w3c.css.sac.LexicalUnit;
import org.w3c.css.sac.NegativeCondition;
import org.w3c.css.sac.NegativeSelector;
import org.w3c.css.sac.PositionalCondition;
import org.w3c.css.sac.ProcessingInstructionSelector;
import org.w3c.css.sac.SACMediaList;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SelectorList;
import org.w3c.css.sac.SiblingSelector;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * This class implements the <code>org.w3c.css.sac.DocumentHandler</code> by generating
 * appropriate SAX events for each parser event. This class is the workhorse of the
 * JXCSS framework.
 * 
 * <br/>
 * The following two properties control the operation of the class:
 * <ul>
 *  <li>
 *      <code>org.xml.sax.ContentHandler contentHandler</code>: This mandatory
 *      property defines the receiver of the SAX events generated in response to
 *      each CSS parser event.
 *  </li>
 *  <li>
 *      <code>java.lang.String namespacePrefix</code>: This optional property controls
 *      the namespace prefix to be output in association with the JXCSS URI
 *      (<code>xcss/1.0</code>). When unspecified, the default prefix is "xcss";
 *  </li>
 * </ul>
 * 
 * <br/>
 * Generated SAX events conform to the
 * <a href="../../css-dtd.html" target="_top">XCSS DTD</a>
 * which models the structure of a CSS stylesheet
 * as defined by the W3C's
 * <a href="http://www.w3.org/TR/2000/REC-DOM-Level-2-Core-20001113/" target="_top">Level-2 DOM</a>. 
 */
public class SAXCSSDocumentHandler implements DocumentHandler {
    /** The XCSS namespace URI (xcss/1.0) */
    public static final String XCSS_NAMESPACE_URI = "xcss/1.0";
    /** The default XCSS namespace prefix (xcss) */
    public static final String DEFAULT_NAMESPACE_PREFIX = "xcss";
    
    public static final String ANCESTOR = "ancestor";
    public static final String AND_CONDITION = "and-condition";
    public static final String ANY_NODE_SELECTOR = "any-node-selector";
    public static final String ATTRIBUTE_CONDITION = "attribute-condition";
    public static final String BEGIN_HYPHEN_ATTRIBUTE_CONDITION = "begin-hyphen-attribute-condition";
    public static final String CDATA_SECTION_NODE_SELECTOR = "cdata-section-node-selector";
    public static final String CHILD = "child";
    public static final String CHILD_SELECTOR = "child-selector";
    public static final String CLASS_CONDITION = "class-condition";
    public static final String COMMENT = "comment";
    public static final String COMMENT_NODE_SELECTOR = "comment-node-selector";
    public static final String CONDITIONAL_SELECTOR = "conditional-selector";
    public static final String CONTENT_CONDITION = "content-condition";
    public static final String CSS_STYLESHEET = "css-stylesheet";
    public static final String DATA = "data";
    public static final String DEFAULT_NAMESPACE_URI = "default-namespace-uri";
    public static final String DESCENDANT_SELECTOR = "descendant-selector";
    public static final String DIRECT_ADJACENT_SELECTOR = "direct-adjacent-selector";
    public static final String ELEMENT_NODE_SELECTOR = "element-node-selector";
    public static final String FIRST_CONDITION = "first-condition";
    public static final String FONT_FACE = "font-face";
    public static final String FUNCTION = "function";
    public static final String ID_CONDITION = "id-condition";
    public static final String IGNORABLE_AT_RULE = "ignorable-at-rule";
    public static final String IMPORT_STYLE = "import-style";
    public static final String IMPORTANT = "important";
    public static final String LANG = "lang";
    public static final String LANG_CONDITION = "lang-condition";
    public static final String LOCAL_NAME = "local-name";
    public static final String MEDIA = "media";
    public static final String MEDIA_FACE = "media-face";
    public static final String MEDIA_LIST = "media-list";
    public static final String NAME = "name";
    public static final String NAMESPACE_DECLARATION = "namespace-declaration";
    public static final String NAMESPACE_URI = "namespace-uri";
    public static final String NEGATIVE_CONDITION = "negative-condition";
    public static final String NEGATIVE_SELECTOR = "negative-selector";
    public static final String ONE_OF_ATTRIBUTE_CONDITION = "one-of-attribute-condition";
    public static final String ONLY_CHILD_CONDITION = "only-child-condition";
    public static final String ONLY_TYPE_CONDITION = "only-type-condition";
    public static final String OR_CONDITION = "or-condition";
    public static final String PAGE = "page";
    public static final String POSITION = "position";
    public static final String POSITIONAL_CONDITION = "positional-condition";
    public static final String PREFIX = "prefix";
    public static final String PROCESSING_INSTRUCTION_NODE_SELECTOR = "processing-instruction-node_selector";
    public static final String PROPERTY = "property";
    public static final String PSEUDO_CLASS_CONDITION = "pseudo-class-condition";
    public static final String PSEUDO_ELEMENT_SELECTOR = "pseudo-element-selector";
    public static final String PSEUDO_PAGE = "pseudo-page";
    public static final String ROOT_NODE_SELECTOR = "root-node-selector";
    public static final String SECOND_CONDITION = "second-condition";
    public static final String SELECTOR = "selector";
    public static final String SIBLING_SELECTOR = "sibling-selector";
    public static final String SPECIFIED = "specified";
    public static final String STYLE = "style";
    public static final String TARGET = "target";
    public static final String TEXT_NODE_SELECTOR = "text-node-selector";
    public static final String TYPE = "type";
    public static final String TYPE_NODE = "type-node";
    public static final String UNIT = "unit";
    public static final String URI = "uri";
    public static final String VALUE = "value";

    private String name;

    private String namespaceUri = XCSS_NAMESPACE_URI;
    private String namespacePrefix = DEFAULT_NAMESPACE_PREFIX;
    
    private ContentHandler contentHandler;
    private AttributesImpl attributes = new AttributesImpl();

    /**
     * The empty constructor. When an instance is created via this empty constructor
     * the method {@link SAXCSSDocumentHandler#setContentHandler(ContentHandler)} must
     * be called before CSS parsing begins.
     *
     */
    public SAXCSSDocumentHandler() {}

    /**
     * The basic constructor. This constructor defines the content handler that will
     * consume generated SAX events.
     * 
     * @param contentHandler The associated content handler
     * 
     * @throws NullPointerException when the passed content handler is null
     */
    public SAXCSSDocumentHandler(ContentHandler contentHandler) {
        setContentHandler(contentHandler);
    }


    /**
     * The complete constructor. This constructor defines the content handler that will
     * consume generated SAX events as well as the namespace prefix to be used in generated
     * SAX events.
     * 
     * @param contentHandler The associated content handler
     * @param namespacePrefix The namespace prefix to be used
     * 
     * @throws NullPointerException when the passed content handler is null
     * @throws NullPointerException when the passed namespace is null
     */
    public SAXCSSDocumentHandler(ContentHandler contentHandler, String namespacePrefix) {
        setContentHandler(contentHandler);
        setNamespacePrefix(namespacePrefix);
    }

    /**
     * Receive notification of the beginning of a style sheet. The CSS parser will invoke this method only once, before any other
     * methods in this interface.
     * 
     * @param is The input source of the current stylesheet. Unused by this implementation
     * 
     * @throws CSSException when a SAX exception is generated by the content handler
     * 
     * @see org.w3c.css.sac.DocumentHandler#startDocument(org.w3c.css.sac.InputSource)
     */
    public void startDocument(InputSource is) throws CSSException {
        try {
            getContentHandler().startDocument();
            if (getName() != null) {
                addAttribute("name", getName());
            }
            startElement(CSS_STYLESHEET);
        } catch (Exception e) {
            throw new CSSException(e);
        }
    }

    /**
     * Receive notification of the end of a document. The CSS parser will invoke this method only once, and it will be the last
     * method invoked during the parse. The parser shall not invoke this method until it has either abandoned parsing (because of an
     * unrecoverable error) or reached the end of input.
     * 
     * @param is The input source of the current stylesheet. Unused by this implementation
     * 
     * @throws CSSException when a SAX exception is generated by the content handler
     * 
     * @see org.w3c.css.sac.DocumentHandler#endDocument(org.w3c.css.sac.InputSource)
     */
    public void endDocument(InputSource is) throws CSSException {
        try {
            endElement(CSS_STYLESHEET);
            getContentHandler().endDocument();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CSSException(e);
        }
    }

    /**
     * Receive notification of a comment. If the comment appears in a declaration (e.g. color: /* comment * / blue;), the parser
     * notifies the comment before the declaration. May be ignored by some SAC parsers.
     * 
     * @param is The input source of the current stylesheet. Unused by this implementation
     * 
     * @throws CSSException when a SAX exception is generated by the content handler
     * 
     * @see org.w3c.css.sac.DocumentHandler#comment(java.lang.String)
     */
    public void comment(String text) throws CSSException {
        try {
            textElement(COMMENT, text);
        } catch (Exception e) {
            throw new CSSException(e);
        }
    }

    /**
     * Receive notification of an unknown rule t-rule not supported by this parser.
     *  
     * @param atRule The complete ignored at-rule.
     * 
     * @throws CSSException when a SAX exception is generated by the content handler
     * 
     * @see org.w3c.css.sac.DocumentHandler#ignorableAtRule(java.lang.String)
     */
    public void ignorableAtRule(String atRule) throws CSSException {
        try {
            textElement(IGNORABLE_AT_RULE, atRule);
        } catch (Exception e) {
            throw new CSSException(e);
        }
    }

    /**
     * Receive notification of a namespace declaration.
     *  
     * @param prefix  <code>null<code> if this is the default namespace
     * @param uri The URI for this namespace.
     * 
     * @throws CSSException when a SAX exception is generated by the content handler
     * 
     * @see org.w3c.css.sac.DocumentHandler#namespaceDeclaration(java.lang.String)
     */
    public void namespaceDeclaration(String prefix, String uri) throws CSSException {
        try {
            addAttribute(PREFIX, prefix);
            addAttribute(URI, uri);
            element(NAMESPACE_DECLARATION);
        } catch (Exception e) {
            throw new CSSException(e);
        }
    }

    /**
     * Receive notification of a import statement in the style sheet.
     *  
     * @param uri The URI of the imported stylesheet.
     * @param media The intended destination media for style information.
     * @param defaultNamepaceURI The default namespace URI for the imported style sheet.
     * 
     * @throws CSSException when a SAX exception is generated by the content handler
     * 
     * @see org.w3c.css.sac.DocumentHandler#importStyle(java.lang.String)
     */
    public void importStyle(String uri, SACMediaList media, String defaultNamespaceURI) throws CSSException {
        try {
            addAttribute(URI, uri);
            addAttribute(DEFAULT_NAMESPACE_URI, uri);
            startElement(IMPORT_STYLE);
            for (int i = 0; i < media.getLength(); i++) {
                textElement(MEDIA_FACE, media.item(i));
            }
            endElement(IMPORT_STYLE);
        } catch (Exception e) {
            throw new CSSException(e);
        }
    }

    /**
     * Receive notification of the beginning of a media statement. The Parser will invoke this method at the beginning of every
     * media statement in the style sheet. There will be a corresponding endMedia() event for every startElement() event.
     *  
     * @param media The intended destination media for style information.
     * 
     * @throws CSSException when a SAX exception is generated by the content handler
     * 
     * @see org.w3c.css.sac.DocumentHandler#startMedia(java.lang.String)
     */
    public void startMedia(SACMediaList media) throws CSSException {
        try {
            startElement(MEDIA_LIST);
            for (int i = 0; i < media.getLength(); i++) {
                textElement(MEDIA_FACE, media.item(i));
            }
        } catch (Exception e) {
            throw new CSSException(e);
        }
    }

    /**
     * Receive notification of the end of a media statement.
     *  
     * @param media The intended destination media for style information.
     * 
     * @throws CSSException when a SAX exception is generated by the content handler
     * 
     * @see org.w3c.css.sac.DocumentHandler#endMedia(java.lang.String)
     */
    public void endMedia(SACMediaList media) throws CSSException {
        try {
            endElement(MEDIA_LIST);
        } catch (Exception e) {
            throw new CSSException(e);
        }
    }

    /**
     * Receive notification of the beginning of a page statement. The Parser will invoke this method at the beginning of every page
     * statement in the style sheet. there will be a corresponding endPage() event for every startPage() event.
     *  
     * @param name  the name of the page (if any, null otherwise)
     * @param pseudoPage  the pseudo page (if any, null otherwise)
     * 
     * @throws CSSException when a SAX exception is generated by the content handler
     * 
     * @see org.w3c.css.sac.DocumentHandler#startPage(java.lang.String)
     */
    public void startPage(String name, String pseudoPage) throws CSSException {
        try {
            addAttribute(NAME, name);
            addAttribute(PSEUDO_PAGE, pseudoPage);
            startElement(PAGE);
        } catch (Exception e) {
            throw new CSSException(e);
        }
    }

    /**
     * Receive notification of the end of a media statement.
     *  
     * @param name  the name of the page (if any, null otherwise)
     * @param pseudoPage  the pseudo page (if any, null otherwise)
     * 
     * @throws CSSException when a SAX exception is generated by the content handler
     * 
     * @see org.w3c.css.sac.DocumentHandler#endPage(java.lang.String)
     */
    public void endPage(String name, String pseudoPage) throws CSSException {
        try {
            endElement(PAGE);
        } catch (Exception e) {
            throw new CSSException(e);
        }
    }

    /**
     * Receive notification of the beginning of a font face statement. The Parser will invoke this method at the beginning of every
     * font face statement in the style sheet. there will be a corresponding endFontFace() event for every startFontFace() event.
     *  
     * @throws CSSException when a SAX exception is generated by the content handler
     * 
     * @see org.w3c.css.sac.DocumentHandler#startFontFace(java.lang.String)
     */
    public void startFontFace() throws CSSException {
        try {
            startElement(FONT_FACE);
        } catch (Exception e) {
            throw new CSSException(e);
        }
    }

    /**
     * Receive notification of the end of a font face statement.
     *  
     * @throws CSSException when a SAX exception is generated by the content handler
     * 
     * @see org.w3c.css.sac.DocumentHandler#endFontFace(java.lang.String)
     */
    public void endFontFace() throws CSSException {
        try {
            endElement(FONT_FACE);
        } catch (Exception e) {
            throw new CSSException(e);
        }
    }

    /**
     * Receive notification of the beginning of a rule statement.

     * @param selectors All intended selectors for all declarations.
     *  
     * @throws CSSException when a SAX exception is generated by the content handler
     * 
     * @see org.w3c.css.sac.DocumentHandler#startSelector(java.lang.String)
     */
    public void startSelector(SelectorList selectors) throws CSSException {
        try {
            startElement(STYLE);
            for (int i = 0; i < selectors.getLength(); i++) {
                selector(selectors.item(i));
            }
        } catch (Exception e) {
            throw new CSSException(e);
        }
    }

    /**
     * Receive notification of the end of a rule statement.

     * @param selectors All intended selectors for all declarations.
     *  
     * @throws CSSException when a SAX exception is generated by the content handler
     * 
     * @see org.w3c.css.sac.DocumentHandler#endSelector(java.lang.String)
     */
    public void endSelector(SelectorList selectors) throws CSSException {
        try {
            endElement(STYLE);
        } catch (Exception e) {
            throw new CSSException(e);
        }
    }

    /**
     * Receive notification of a declaration.
     *
     * @param name  the name of the property.
     * @param value  the value of the property. All whitespace are stripped.
     * @param important is this property important?
     *  
     * @throws CSSException when a SAX exception is generated by the content handler
     * 
     * @see org.w3c.css.sac.DocumentHandler#property(java.lang.String)
     */
    public void property(String name, LexicalUnit lexicalUnit, boolean important) throws CSSException {
        try {
            addAttribute(NAME, name);
            if (important) {
                addAttribute(IMPORTANT, Boolean.valueOf(important).toString());
            }
            startElement(PROPERTY);
            lexicalValue(lexicalUnit);
            endElement(PROPERTY);
        } catch (Exception e) {
            throw new CSSException(e);
        }
    }

    protected void selector(Selector selector) throws SAXException {
        switch (selector.getSelectorType()) {
            case Selector.SAC_ANY_NODE_SELECTOR: {
                anyNodeSelector();
                break;
            }
            case Selector.SAC_CHILD_SELECTOR: {
                descendantSelector((DescendantSelector) selector, CHILD_SELECTOR);
                break;
            }
            case Selector.SAC_CDATA_SECTION_NODE_SELECTOR: {
                characterDataSelector((CharacterDataSelector) selector, CDATA_SECTION_NODE_SELECTOR);
                break;
            }
            case Selector.SAC_DESCENDANT_SELECTOR: {
                descendantSelector((DescendantSelector) selector, DESCENDANT_SELECTOR);
                break;
            }
            case Selector.SAC_COMMENT_NODE_SELECTOR: {
                characterDataSelector((CharacterDataSelector) selector, COMMENT_NODE_SELECTOR);
                break;
            }
            case Selector.SAC_CONDITIONAL_SELECTOR: {
                conditionalSelector((ConditionalSelector) selector);
                break;
            }
            case Selector.SAC_DIRECT_ADJACENT_SELECTOR: {
                siblingSelector((SiblingSelector) selector);
                break;
            }
            case Selector.SAC_ELEMENT_NODE_SELECTOR: {
                elementSelector((ElementSelector) selector, ELEMENT_NODE_SELECTOR);
                break;
            }
            case Selector.SAC_NEGATIVE_SELECTOR: {
                negativeSelector((NegativeSelector) selector);
                break;
            }
            case Selector.SAC_PROCESSING_INSTRUCTION_NODE_SELECTOR: {
                processingInstructionSelector((ProcessingInstructionSelector) selector);
                break;
            }
            case Selector.SAC_PSEUDO_ELEMENT_SELECTOR: {
                elementSelector((ElementSelector) selector, PSEUDO_ELEMENT_SELECTOR);
                break;
            }
            case Selector.SAC_ROOT_NODE_SELECTOR: {
                rootNodeSelector();
                break;
            }
            case Selector.SAC_TEXT_NODE_SELECTOR: {
                characterDataSelector((CharacterDataSelector) selector, TEXT_NODE_SELECTOR);
                break;
            }
        }
    }

    protected void anyNodeSelector() throws SAXException {
        element(ANY_NODE_SELECTOR);
    }

    protected void descendantSelector(DescendantSelector descendantSelector, String elementName) throws SAXException {
        startElement(elementName);
        startElement(ANCESTOR);
        selector(descendantSelector.getAncestorSelector());
        endElement(ANCESTOR);
        startElement(CHILD);
        selector(descendantSelector.getSimpleSelector());
        endElement(CHILD);
        endElement(elementName);
    }

    protected void characterDataSelector(CharacterDataSelector characterDataSelector, String elementName) throws SAXException {
        textElement(elementName, characterDataSelector.getData());
    }

    protected void conditionalSelector(ConditionalSelector conditionalSelector) throws SAXException {
        startElement(CONDITIONAL_SELECTOR);
        startElement(SELECTOR);
        selector(conditionalSelector.getSimpleSelector());
        endElement(SELECTOR);
        condition(conditionalSelector.getCondition());
        endElement(CONDITIONAL_SELECTOR);
    }

    protected void siblingSelector(SiblingSelector siblingSelector) throws SAXException {
        // TODO: Map integer types to DOM node type names
        addAttribute("node-type", Integer.toString(siblingSelector.getNodeType()));
        startElement(DIRECT_ADJACENT_SELECTOR);
        startElement(SELECTOR);
        selector(siblingSelector.getSelector());
        endElement(SELECTOR);
        startElement(SIBLING_SELECTOR);
        selector(siblingSelector.getSiblingSelector());
        endElement(SIBLING_SELECTOR);
        endElement(DIRECT_ADJACENT_SELECTOR);
    }

    protected void elementSelector(ElementSelector elementSelector, String elementName) throws SAXException {
        addAttribute(LOCAL_NAME, elementSelector.getLocalName());
        addAttribute(NAMESPACE_URI, elementSelector.getNamespaceURI());
        element(elementName);
    }

    protected void negativeSelector(NegativeSelector negativeSelector) throws SAXException {
        startElement(NEGATIVE_SELECTOR);
        selector(negativeSelector.getSimpleSelector());
        endElement(NEGATIVE_SELECTOR);
    }

    protected void processingInstructionSelector(ProcessingInstructionSelector processingInstructionSelector) throws SAXException {
        addAttribute(DATA, processingInstructionSelector.getData());
        addAttribute(TARGET, processingInstructionSelector.getTarget());
        element(PROCESSING_INSTRUCTION_NODE_SELECTOR);
    }

    protected void rootNodeSelector() throws SAXException {
        element(ROOT_NODE_SELECTOR);
    }

    protected void condition(Condition condition) throws SAXException {
        switch (condition.getConditionType()) {
            case Condition.SAC_AND_CONDITION: {
                combinatorCondition((CombinatorCondition) condition, AND_CONDITION);
                break;
            }
            case Condition.SAC_OR_CONDITION: {
                combinatorCondition((CombinatorCondition) condition, OR_CONDITION);
                break;
            }
            case Condition.SAC_NEGATIVE_CONDITION: {
                negativeCondition((NegativeCondition) condition);
                break;
            }
            case Condition.SAC_POSITIONAL_CONDITION: {
                positionalCondition((PositionalCondition) condition);
                break;
            }
            case Condition.SAC_ATTRIBUTE_CONDITION: {
                attributeCondition((AttributeCondition) condition, ATTRIBUTE_CONDITION);
                break;
            }
            case Condition.SAC_ID_CONDITION: {
                attributeCondition((AttributeCondition) condition, ID_CONDITION);
                break;
            }
            case Condition.SAC_LANG_CONDITION: {
                langCondition((LangCondition) condition);
                break;
            }
            case Condition.SAC_ONE_OF_ATTRIBUTE_CONDITION: {
                attributeCondition((AttributeCondition) condition, ONE_OF_ATTRIBUTE_CONDITION);
                break;
            }
            case Condition.SAC_BEGIN_HYPHEN_ATTRIBUTE_CONDITION: {
                attributeCondition((AttributeCondition) condition, BEGIN_HYPHEN_ATTRIBUTE_CONDITION);
                break;
            }
            case Condition.SAC_CLASS_CONDITION: {
                attributeCondition((AttributeCondition) condition, CLASS_CONDITION);
                break;
            }
            case Condition.SAC_PSEUDO_CLASS_CONDITION: {
                attributeCondition((AttributeCondition) condition, PSEUDO_CLASS_CONDITION);
                break;
            }
            case Condition.SAC_ONLY_CHILD_CONDITION: {
                onlyChildCondition();
                break;
            }
            case Condition.SAC_ONLY_TYPE_CONDITION: {
                onlyTypeCondition();
                break;
            }
            case Condition.SAC_CONTENT_CONDITION: {
                contentCondition((ContentCondition) condition);
                break;
            }
        }
    }

    protected void combinatorCondition(CombinatorCondition combinatorCondition, String elementName) throws SAXException {
        startElement(elementName);
        startElement(FIRST_CONDITION);
        condition(combinatorCondition.getFirstCondition());
        endElement(FIRST_CONDITION);
        startElement(SECOND_CONDITION);
        condition(combinatorCondition.getSecondCondition());
        endElement(SECOND_CONDITION);
        endElement(elementName);
    }

    protected void negativeCondition(NegativeCondition negativeCondition) throws SAXException {
        startElement(NEGATIVE_CONDITION);
        condition(negativeCondition.getCondition());
        endElement(NEGATIVE_CONDITION);
    }

    protected void positionalCondition(PositionalCondition positionalCondition) throws SAXException {
        addAttribute(POSITION, Integer.toString(positionalCondition.getPosition()));
        addAttribute(TYPE, Boolean.toString(positionalCondition.getType()));
        addAttribute(TYPE, Boolean.toString(positionalCondition.getTypeNode()));
        element(POSITIONAL_CONDITION);
    }

    protected void attributeCondition(AttributeCondition attributeCondition, String elementName) throws SAXException {
        addAttribute(LOCAL_NAME, attributeCondition.getLocalName());
        addAttribute(NAMESPACE_URI, attributeCondition.getNamespaceURI());
        addAttribute(SPECIFIED, Boolean.toString(attributeCondition.getSpecified()));
        textElement(elementName, attributeCondition.getValue());
    }

    protected void langCondition(LangCondition langCondition) throws SAXException {
        addAttribute(LANG, langCondition.getLang());
        element(LANG_CONDITION);
    }

    protected void onlyChildCondition() throws SAXException {
        element(ONLY_CHILD_CONDITION);
    }

    protected void onlyTypeCondition() throws SAXException {
        element(ONLY_TYPE_CONDITION);
    }

    protected void contentCondition(ContentCondition contentCondition) throws SAXException {
        textElement(CONTENT_CONDITION, contentCondition.getData());
    }

    protected void lexicalValue(LexicalUnit lexicalUnit) throws SAXException {
        while (lexicalUnit != null) {
            int lexicalUnitType = lexicalUnit.getLexicalUnitType();
            switch (lexicalUnitType) {
                case LexicalUnit.SAC_INTEGER:
                    addAttribute(TYPE, "integer");
                    textElement(VALUE, Integer.toString(lexicalUnit.getIntegerValue()));
                    break;
                case LexicalUnit.SAC_REAL:
                case LexicalUnit.SAC_DIMENSION:
                case LexicalUnit.SAC_EM:
                case LexicalUnit.SAC_EX:
                case LexicalUnit.SAC_PIXEL:
                case LexicalUnit.SAC_INCH:
                case LexicalUnit.SAC_CENTIMETER:
                case LexicalUnit.SAC_MILLIMETER:
                case LexicalUnit.SAC_POINT:
                case LexicalUnit.SAC_PICA:
                case LexicalUnit.SAC_PERCENTAGE:
                case LexicalUnit.SAC_DEGREE:
                case LexicalUnit.SAC_GRADIAN:
                case LexicalUnit.SAC_RADIAN:
                case LexicalUnit.SAC_MILLISECOND:
                case LexicalUnit.SAC_SECOND:
                case LexicalUnit.SAC_HERTZ:
                case LexicalUnit.SAC_KILOHERTZ:
                    addAttribute(TYPE, "float");
                    addAttribute(UNIT, lexicalUnit.getDimensionUnitText());
                    // Supress trailing zeroes in float value
                    String floatValue = Float.toString(lexicalUnit.getFloatValue());
                    floatValue = floatValue.replaceFirst("0+$", "");
                    textElement(VALUE, floatValue);
                    break;
                case LexicalUnit.SAC_URI:
                    addAttribute(TYPE, "uri");
                    textElement(VALUE, lexicalUnit.getStringValue());
                    break;
                case LexicalUnit.SAC_IDENT:
                case LexicalUnit.SAC_STRING_VALUE:
                case LexicalUnit.SAC_UNICODERANGE:
                    addAttribute(TYPE, "string");
                    textElement(VALUE, lexicalUnit.getStringValue());
                    break;
                case LexicalUnit.SAC_ATTR:
                case LexicalUnit.SAC_COUNTER_FUNCTION:
                case LexicalUnit.SAC_COUNTERS_FUNCTION:
                case LexicalUnit.SAC_RECT_FUNCTION:
                case LexicalUnit.SAC_FUNCTION:
                case LexicalUnit.SAC_RGBCOLOR:
                    addAttribute(NAME, lexicalUnit.getFunctionName());
                    startElement(FUNCTION);
                    lexicalValue(lexicalUnit.getParameters());
                    endElement(FUNCTION);
                    break;
                case LexicalUnit.SAC_INHERIT:
                    textElement(VALUE, "inherit");
                    break;
                case LexicalUnit.SAC_OPERATOR_COMMA:
                    // Simple separator
                    break;
                case LexicalUnit.SAC_SUB_EXPRESSION:
                default:
                    // TODO: Log this!
                    textElement("unknown", "unknown #" + Integer.toString(lexicalUnit.getLexicalUnitType()) + " {" + lexicalUnit.toString() + "}");
            }
            lexicalUnit = lexicalUnit.getNextLexicalUnit();
        }
    }

    private void startElement(String elementName) {
        try {
            getContentHandler().startElement(getNamespaceUri(), elementName, qName(elementName), attributes);
            attributes.clear();
        } catch (SAXException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void endElement(String elementName) {
        try {
            getContentHandler().endElement(getNamespaceUri(), elementName, qName(elementName));
        } catch (SAXException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void element(String elementName) {
        try {
            String qName = qName(elementName);
            getContentHandler().startElement(getNamespaceUri(), elementName, qName, attributes);
            getContentHandler().endElement(getNamespaceUri(), elementName, qName);
            attributes.clear();
        } catch (SAXException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void textElement(String elementName, String text) {
        try {
            String qName = qName(elementName);
            getContentHandler().startElement(getNamespaceUri(), elementName, qName, attributes);
            text(text);
            getContentHandler().endElement(getNamespaceUri(), elementName, qName);
            attributes.clear();
        } catch (SAXException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void text(String text) {
        try {
            if (text != null) {
                getContentHandler().characters(text.toCharArray(), 0, text.length());
            }
        } catch (SAXException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void addAttribute(String name, String value) {
        if (value == null) {
            value = "";
        }
        //attributes.addAttribute("", name, qName(name), "", value);
        attributes.addAttribute("", name, name, "", value);
    }

    private String qName(String elementName) {
        if ("".equals(getNamespacePrefix())) {
            return elementName;
        }
        return getNamespacePrefix() + ":" + elementName;
    }

    /**
     * 
     * @param contentHandler The associated content handler
     * 
     * @throws NullPointerException when the passed content handler is null
     */
    public void setContentHandler(ContentHandler contentHandler) {
        if (contentHandler == null) {
            throw new NullPointerException("Content handler cannot be null");
        }
        this.contentHandler = contentHandler;
    }

    /**
     * @return Returns the contentHandler.
     */
    public ContentHandler getContentHandler() {
        return contentHandler;
    }

    /**
     * @param namespacePrefix
     *            The namespacePrefix to set.
     */
    public void setNamespacePrefix(String namespacePrefix) {
        this.namespacePrefix = namespacePrefix;
    }

    /**
     * @return Returns the namespacePrefix.
     */
    public String getNamespacePrefix() {
        return namespacePrefix;
    }

    /**
     * @param namespaceUri The namespaceUri to set.
     */
    public void setNamespaceUri(String namespaceUri) {
        this.namespaceUri = namespaceUri;
    }

    /**
     * @return Returns the namespaceUri.
     */
    public String getNamespaceUri() {
        return namespaceUri;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
}
