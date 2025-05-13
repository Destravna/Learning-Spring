package Chap3.configuration;

import org.dhruv.Chap2.decoupled.HelloWorldMessageProvider;
import org.dhruv.Chap2.decoupled.MessageProvider;
import org.dhruv.Chap2.decoupled.MessageRenderer;
import org.dhruv.Chap2.decoupled.StdOutMessageRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfiguration {
    
    @Bean
    public MessageProvider provider(){
        return new HelloWorldMessageProvider();
    }

    @Bean
    public MessageRenderer renderer(){
        MessageRenderer renderer = new StdOutMessageRenderer();
        renderer.setMessageProvider(provider());
        return renderer;
    }

}
