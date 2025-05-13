package Chap3.withstypes;

import org.dhruv.Chap2.decoupled.MessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigurableMessageProvider implements MessageProvider {
    private String message;

    @Autowired
    ConfigurableMessageProvider(@Value("Configurable Message") String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
