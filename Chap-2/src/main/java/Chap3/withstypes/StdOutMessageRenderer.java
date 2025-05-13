package Chap3.withstypes;

import org.dhruv.Chap2.decoupled.MessageProvider;
import org.dhruv.Chap2.decoupled.MessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StdOutMessageRenderer implements MessageRenderer {
    private MessageProvider messageProvider;

    @Autowired
    @Override
    public void setMessageProvider(MessageProvider provider) {
        System.out.println("setter injection");
        this.messageProvider = provider;
    }

    @Override
    public void render() {
        System.out.println(messageProvider.getMessage());

    }

    @Override
    public MessageProvider getMessageProvider() {
       return messageProvider;
    }
}
