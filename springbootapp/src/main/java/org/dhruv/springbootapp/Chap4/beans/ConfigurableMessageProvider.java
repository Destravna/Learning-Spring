package org.dhruv.springbootapp.Chap4.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.dhruv.Chap2.decoupled.MessageProvider;
import org.junit.jupiter.api.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Order(1)
@Component
public class ConfigurableMessageProvider implements MessageProvider, CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurableMessageProvider.class);
    private String message = "default message";
    
    public ConfigurableMessageProvider(@Value("Configurable message") String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void run(String... args) throws Exception {
        if(args.length >= 1){
            message = args[0];
        }
    }

    
}
