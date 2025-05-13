package Chapter4;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class Singer {
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

    public void init() {
        logger.info("Initializing bean");
        if (name == null) {
            logger.info("Using default name");
            name = DEFAULT_NAME;
        }
        if (age == Integer.MIN_VALUE) {
            throw new IllegalArgumentException(
                    "You must set the age property of any beans of type " + Singer.class);
        }

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("age", age)
                .toString();

    }

}

@Configuration
class SingerConfiguration {
    
    @Bean(initMethod = "init")
    Singer singerOne(){
        Singer singer = new Singer();
        singer.setName("John Mayer");
        singer.setAge(43);
        return singer;
    }

    @Bean(initMethod = "init")
    Singer singerTwo(){
        Singer singer = new Singer();
        singer.setAge(42);
        return singer;
    }

    @Bean(initMethod = "init")
    Singer singerThree(){
        Singer singer = new Singer();
        singer.setAge(43);
        singer.setName("John Butler");
        return singer;
    }
}

public class InitMethodDemo {
    private static Logger logger = LoggerFactory.getLogger(InitMethodDemo.class);
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(SingerConfiguration.class);

        getBean("singerOne", ctx);
        getBean("singerTwo", ctx);
        getBean("singerThree", ctx);
        
    }

      public static Singer getBean(String beanName, ApplicationContext ctx) {
        try {
            Singer bean = (Singer) ctx.getBean(beanName);
            logger.info("Found: {}", bean);
            return bean;
        } catch (BeanCreationException ex) {
            logger.error("An error occured in bean configuration: " + ex.getMessage());
            return null;
        }
    }




}
