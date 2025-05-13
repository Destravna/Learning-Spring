package Chap3.withstypes;

import org.dhruv.Chap2.decoupled.MessageProvider;
import org.dhruv.Chap2.decoupled.MessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StdOutMessageRendererConstructorInjection implements MessageRenderer {
    private MessageProvider messageProvider;

    // @Autowired
    StdOutMessageRendererConstructorInjection(MessageProvider messageProvider){
        this.messageProvider = messageProvider;
    }

    @Override
    public void render() {
        System.out.println(messageProvider.getMessage());
        
    }

    @Override
    public MessageProvider getMessageProvider() {
        return messageProvider;
    }

    @Override
    public void setMessageProvider(MessageProvider provider) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setMessageProvider'");
    }
}
