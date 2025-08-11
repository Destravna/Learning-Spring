package org.dhruv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;

import Chap11.PropertyEditingSomething.Address;
import Chap11.PropertyEditingSomething.AppConfig;
import Chap11.PropertyEditingSomething.FormattingServiceCnfg;
import Chap11.ValidationSomething.AddressValidator;
import Chap11.ValidationSomething.BloggerWithAddressValidator;
import Chap11.ValidationSomething.ComplexBloggerValidator;
import Chap11.ValidationSomething.SimpleBloggerValidator;
import Chap11.PropertyEditingSomething.Blogger;
import Chap11.PropertyEditingSomething.BloggerWithAddress;

public class SpringValidatorTest {
    private static final Logger logger = LoggerFactory.getLogger(SpringValidatorTest.class);

    @Test
    public void testSimpleBloggerValidator() throws MalformedURLException {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class, FormattingServiceCnfg.class,
                SimpleBloggerValidator.class)) {
            var blogger = new Blogger("", "Pedala", LocalDate.of(2000, 1, 1), new URL("https://none.co.uk"));
            var bloggerValidator = ctx.getBean(SimpleBloggerValidator.class);
            assertNotNull(bloggerValidator);
            var result = new BeanPropertyBindingResult(blogger, "blogger"); // acts as a container to hold validation
                                                                            // errors for specific objct, blogger is the
                                                                            // object, and string is the name of the
                                                                            // object used for key
            ValidationUtils.invokeValidator(bloggerValidator, blogger, result);
            List<ObjectError> errors = result.getAllErrors();
            assertEquals(1, errors.size());
            errors.forEach(
                    e -> logger.info("Object '{}' failed validation. Error code: {}", e.getObjectName(), e.getCode()));

        }
    }

    @Test
    public void complexBloggerValidator() throws MalformedURLException {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class, FormattingServiceCnfg.class,
                ComplexBloggerValidator.class)) {
            var blogger = new Blogger("", "", LocalDate.of(2008, 1, 1), new URL("https://none.co.uk"));
            var bloggerValidator = ctx.getBean(ComplexBloggerValidator.class);
            assertNotNull(bloggerValidator);
            var result = new BeanPropertyBindingResult(blogger, "blogger");
            ValidationUtils.invokeValidator(bloggerValidator, blogger, result);
            List<ObjectError> errors = result.getAllErrors();
            assertEquals(3, errors.size());
            errors.forEach(
                    e -> logger.info("Object '{}' failed validation. Error code: {}", e.getObjectName(), e.getCode()));

        }
    }

    @Test
    public void BloggerWithAddressValidator() throws MalformedURLException {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class, FormattingServiceCnfg.class, Chap11.ValidationSomething.BloggerWithAddressValidator.class, AddressValidator.class)) {
            var address = new Address("221B", "UK");
            var propertyEditingSomething = new Chap11.PropertyEditingSomething();
            var blogger = propertyEditingSomething.new BloggerWithAddress(null, "Mazzie", LocalDate.of(1973, 1, 1), null, address);
            var bloggerValidator = ctx.getBean(BloggerWithAddressValidator.class);
            var result = new BeanPropertyBindingResult(blogger, "blogger");
            ValidationUtils.invokeValidator(bloggerValidator, blogger, result);
            var errors = result.getAllErrors();
            assertEquals(4, errors.size());
            errors.forEach(e -> logger.info("Error Code: {}", e.getCode()));
        }
    }

}
