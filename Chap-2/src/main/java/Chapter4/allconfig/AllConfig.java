package Chapter4.allconfig;

import org.dhruv.Chap2.decoupled.MessageProvider;
import org.dhruv.Chap2.decoupled.MessageRenderer;
import org.dhruv.Chap2.decoupled.StdOutMessageRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import Chapter4.integrationTestingSomething.provider.DsProvider;

@Configuration
public class AllConfig {
    
    @Profile("Dev")
    @Bean
    MessageProvider messageProvider(){
        return new DsProvider();
    }

    @Bean
    MessageRenderer messageRenderer(){
        MessageRenderer mr = new StdOutMessageRenderer();
        mr.setMessageProvider(messageProvider());
        return mr;
    }

}
