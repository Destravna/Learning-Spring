package Chapter4;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

class Singer2 {
    private static Logger logger = LoggerFactory.getLogger(Singer.class);

    private static String DEFAULT_NAME = "Dhruv The Great Inventor";
    private String name;
    private Integer age;

    public void setAge(Integer age) {
        logger.info("Calling setAge for bean of type {}.", Singer.class);
        this.age = age;
    }

    public void setName(String name) {
        logger.info("Calling setName for bean of type {}.", Singer.class);
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    // public void init() {
    //     logger.info("Initializing bean");
    //     if (name == null) {
    //         logger.info("Using default name");
    //         name = DEFAULT_NAME;
    //     }
    //     if (age == Integer.MIN_VALUE) {
    //         throw new IllegalArgumentException(
    //                 "You must set the age property of any beans of type " + Singer.class);
    //     }

    // }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("age", age)
                .toString();

    }

    @PostConstruct
    public void print() {
        String s = new ToStringBuilder(this)
                .append("name", name)
                .append("age", age).append("msg", "postconstruction called")
                .toString();

        logger.info(s);

    }

}

@Configuration
class SingerConfiguration2 {

    @Bean
    Singer2 singerOne() {
        Singer2 singer = new Singer2();
        singer.setName("John Mayer");
        singer.setAge(43);
        return singer;
    }

    @Bean
    Singer2 singerTwo() {
        Singer2 singer = new Singer2();
        singer.setAge(42);
        return singer;
    }

    @Bean
    Singer2 singerThree() {
        Singer2 singer = new Singer2();
        singer.setAge(43);
        singer.setName("John Butler");
        return singer;
    }
}

public class PostCreationDestroyDemo {
    private static Logger logger = LoggerFactory.getLogger(PostCreationDestroyDemo.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(SingerConfiguration2.class);

        getBean("singerOne", ctx);
        getBean("singerTwo", ctx);
        getBean("singerThree", ctx);        
    }

    public static Singer2 getBean(String beanName, ApplicationContext ctx) {
        try {
            Singer2 bean = (Singer2) ctx.getBean(beanName);
            logger.info("Found: {}", bean);
            return bean;
        } catch (BeanCreationException ex) {
            logger.error("An error occured in bean configuration: " + ex.getMessage());
            return null;
        }
    }

}
