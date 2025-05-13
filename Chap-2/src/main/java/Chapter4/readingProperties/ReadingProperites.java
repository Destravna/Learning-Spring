package Chapter4.readingProperties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import jakarta.annotation.PostConstruct;

class AppProperty {
    private String applicaitonName;
    private String userHome;

    public String getApplicaitonName() {
        return applicaitonName;
    }

    public String getUserHome() {
        return userHome;
    }

    @Autowired
    public void setUserHome(@Value("${user.home}") String userHome) {
        this.userHome = userHome;
    }

    @Autowired
    public void setApplicaitonName(@Value("${applicaiton.name}") String applicaitonName) {
        this.applicaitonName = applicaitonName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("applicationHome", applicaitonName)
                .append("userHome", userHome)
                .toString();
    }
}

@Configuration
@PropertySource("classpath:application.properties")
class PropertyDemoConfig {
    @Autowired
    StandardEnvironment env;

    @PostConstruct
    void configPriority(){
        ResourcePropertySource rps = (ResourcePropertySource)env.getPropertySources().stream().filter(ps -> ps instanceof ResourcePropertySource).findAny().orElse(null);
        env.getPropertySources().addFirst(rps);
    }

    @Bean 
    AppProperty appProperty(){
        return new AppProperty();
    }

}

public class ReadingProperites {

    private static Logger logger = LoggerFactory.getLogger(ReadingProperites.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(PropertyDemoConfig.class);

        var appProperty = ctx.getBean(AppProperty.class);
        logger.info("outcome : {}", appProperty);
    }

}
