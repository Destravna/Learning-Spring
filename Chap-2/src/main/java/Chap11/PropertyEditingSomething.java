package Chap11;

import java.beans.PropertyEditorSupport;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyEditingSomething {
    private static final Logger logger = LoggerFactory.getLogger(PropertyEditingSomething.class);

    public static void main(String[] args) {
        // var ctx = new AnnotationConfigApplicationContext(AppConfig.class,
        // CustomRegistrarConfig.class); //testing the CustomPropertyRegistrar
        // var ctx = new AnnotationConfigApplicationContext(AppConfig.class,
        // ConverterCnfg.class);
        // var ctx = new AnnotationConfigApplicationContext(AppConfig.class,
        // ApplicationConversionServiceFactoryBean.class, ConverterCnfg.class);
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class, FormattingServiceCnfg.class,
                ConverterCnfg.class);
        var alex = ctx.getBean("awsBlogger");
        var dhruv = ctx.getBean("springBlogger");
        var conversionService = ctx.getBean("bloggerToSimpleBlogger", ConversionService.class);
        logger.info("alex : {}", alex);
        logger.info("dhruv : {}", dhruv);
        var simpleBlogger = conversionService.convert(dhruv, SimpleBlogger.class);
        logger.info("simple blogger : {} ", simpleBlogger);
        ctx.close();
    }

    @Configuration
    @PropertySource("classpath:blogger.properties")
    public static class AppConfig {
        @Bean
        public Blogger awsBlogger(
                @Value("Alex") String firstName,
                @Value("DeBrie") String lastName,
                @Value("https://www.alexdebrie.com") URL personalSite,
                @Value("01-02-1980") LocalDate birthDate) throws Exception {
            return new Blogger(firstName, lastName, birthDate, personalSite);
        }

        @Bean
        public Blogger springBlogger(
                @Value("${springBlogger.firstName}") String firstName,
                @Value("${springBlogger.lastName}") String lastName,
                @Value("${springBlogger.personalSite}") URL personalSite,
                @Value("${springBlogger.birthDate}") LocalDate birthDate) // spring cannot directly convert from spring
                                                                          // to
                                                                          // localDate, so we need to create a custom
                                                                          // editor
                throws Exception {

            return new Blogger(firstName, lastName, birthDate, personalSite);
        }

    }

    public record Blogger(String firstName, String lastName, LocalDate birthDate, URL personalSite) {
    }

    public record Address(String city, String country) {
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class BloggerWithAddress {
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private URL personalSite;
        private Address address;
    }

    @Configuration
    public static class FormattingServiceCnfg {
        @Bean
        public FormattingConversionService conversionService() {
            var formattingConversionServiceBean = new DefaultFormattingConversionService(true);
            formattingConversionServiceBean.addFormatter(localDateFormatter());
            // we can also add converters into this.
            return formattingConversionServiceBean;
        }

        @Bean
        protected Formatter<LocalDate> localDateFormatter() {
            return new Formatter<LocalDate>() {
                @Override
                public LocalDate parse(String text, Locale locale) throws ParseException {
                    return LocalDate.parse(text, getDateTimeFormatter());
                }

                @Override
                public String print(LocalDate object, Locale locale) {
                    return object.format(getDateTimeFormatter());
                }

                public DateTimeFormatter getDateTimeFormatter() {
                    return DateTimeFormatter.ofPattern("dd-MM-yyyy");
                }

            };
        }

    }
}

class LocalDatePropertyEditor extends PropertyEditorSupport {
    private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(LocalDate.parse(text, dateTimeFormat));
    }
}

@Configuration
class CustomRegistrarConfig {
    // this is to tell whenever a localdate needs to be bound form a string use,
    // this specific editor.
    @Bean
    public PropertyEditorRegistrar registrar() {
        return registry -> registry.registerCustomEditor(LocalDate.class, new LocalDatePropertyEditor());
    }

    // actuall hook, applies the propertyEditorRegistrar to every data binder in the
    // app.
    @Bean
    public CustomEditorConfigurer customEditorConfigurer() {
        var cus = new CustomEditorConfigurer();
        var registrars = new PropertyEditorRegistrar[1];
        registrars[0] = registrar();
        cus.setPropertyEditorRegistrars(registrars);
        return cus;
        /*
         * or we can do this.
         * var cus = new CustomEditorConfigurer();
         * cus.setCustomEditors(Map.of(LocalDate.class, LocalDatePropertyEditor.class));
         * return cus;
         */
    }
}

// old way above, this is the new way
class LocalDateConverter implements Converter<String, LocalDate> {
    private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source, dateTimeFormat);
    }
}

@Configuration
class ConverterCnfg {

    @Bean(name = "bloggerToSimpleBlogger")
    public ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
        Set<Converter<?, ?>> convs = new HashSet<>();
        // convs.add(new LocalDateConverter());
        convs.add(new SimpleBlogger.BloggerToSimpleBlogger());
        conversionServiceFactoryBean.setConverters(convs);
        conversionServiceFactoryBean.afterPropertiesSet();
        return conversionServiceFactoryBean;
    }
}

// conversion to arbitary objects
record SimpleBlogger(String fullName, URL personalSite) {
    public static class BloggerToSimpleBlogger
            implements Converter<Chap11.PropertyEditingSomething.Blogger, SimpleBlogger> {
        @Override
        public SimpleBlogger convert(Chap11.PropertyEditingSomething.Blogger source) {
            return new SimpleBlogger(source.firstName() + " " + source.lastName(), source.personalSite());
        }
    }
}

// Formatters
@Service("conversionService")
class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConversionServiceFactoryBean.class);
    private static final String DEFAULT_DATE_PATTERN = "dd-MM-yyyy";
    private DateTimeFormatter dateTimeFormatter;
    private String datePattern = DEFAULT_DATE_PATTERN;
    private final Set<Formatter<?>> formatters = new HashSet<>();

    public String getDatePattern() {
        return datePattern;
    }

    @Autowired(required = false)
    public void setDatePattern() {
        this.datePattern = datePattern;
    }

    public Formatter<LocalDate> getDateTimeFormatter() {
        return new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String text, Locale locale) throws ParseException {
                return LocalDate.parse(text, dateTimeFormatter);
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return object.format(dateTimeFormatter);
            }
        };
    }

    @PostConstruct
    public void init() {
        dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        formatters.add(getDateTimeFormatter());
        setFormatters(formatters);
    }
}

// Eaiser way of the above
