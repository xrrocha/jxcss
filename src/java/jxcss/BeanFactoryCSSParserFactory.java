package jxcss;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.w3c.css.sac.Parser;

public class BeanFactoryCSSParserFactory extends AbstractCSSParserFactory implements BeanFactoryAware {
    private BeanFactory beanFactory;
    
    public void setBeanFactory(BeanFactory beanFactory) {
    	this.beanFactory = beanFactory;
    }

    public Parser newParserFor(String name) throws IllegalArgumentException {
        try {
            return (Parser) beanFactory.getBean(name);
        } catch (NoSuchBeanDefinitionException nsbe) {
            throw new IllegalArgumentException("No such CSS parser: " + name);
        } catch (ClassCastException cce) {
            throw new IllegalArgumentException("Given component is not a CSS parser: " + name);
        }
    }
}
