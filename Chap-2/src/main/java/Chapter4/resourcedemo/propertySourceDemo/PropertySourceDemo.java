package Chapter4.resourcedemo.propertySourceDemo;

import org.dhruv.Chap2.decoupled.MessageProvider;
import org.dhruv.Chap2.decoupled.MessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import Chap3.withstypes.StdOutMessageRenderer;

public class PropertySourceDemo {

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(PropertySourceConfig.class);
        MessageRenderer mr = ctx.getBean(MessageRenderer.class);
        mr.render();
        
    }

}

@Component
class ConfigurableMessageProvider implements MessageProvider {
    
    private String message;

    public ConfigurableMessageProvider(@Value("Configurable message") String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}


@Configuration
@PropertySource(value = "classpath:message.properties")
class PropertySourceConfig{
    @Autowired
    Environment env;

    @Bean
    @Lazy
    public MessageProvider messageProvider(){
        return new ConfigurableMessageProvider(env.getProperty("message"));
    }    

    @Bean
    public MessageRenderer messageRenderer(){
        MessageRenderer renderer = new StdOutMessageRenderer();
        renderer.setMessageProvider(messageProvider());
        return renderer;
    }
}