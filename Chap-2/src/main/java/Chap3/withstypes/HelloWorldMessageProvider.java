package Chap3.withstypes;

import org.dhruv.Chap2.decoupled.MessageProvider;
import org.springframework.stereotype.Component;

// @Component("provider")
public class HelloWorldMessageProvider implements MessageProvider {
    @Override
    public String getMessage() {
        return "Hello world from component provider";
    }

}
