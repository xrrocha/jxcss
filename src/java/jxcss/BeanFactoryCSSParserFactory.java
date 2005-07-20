package jxcss;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.w3c.css.sac.Parser;

public class BeanFactoryCSSParserFactory extends AbstractCSSParserFactory {
    private BeanFactory beanFactory;
    
    public BeanFactoryCSSParserFactory() {
        Thread currentThread = Thread.currentThread();
        ClassLoader classLoader = currentThread.getContextClassLoader();
        try {
            beanFactory = new XmlBeanFactory(new ClassPathResource("jxcss/css-parser-factory.xml")); 
        } finally {
            currentThread.setContextClassLoader(classLoader);
        }
        
    }

    public Parser newParserFor(String name) throws IllegalArgumentException {
        try {
            return (Parser) beanFactory.getBean(name);
        } catch (NoSuchBeanDefinitionException nsbe) {
            throw new IllegalArgumentException("No such CSS parser: " + name);
        }
    }
}
