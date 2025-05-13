package Chapter4.integrationTestingSomething.renderer;

import org.dhruv.Chap2.decoupled.MessageProvider;
import org.dhruv.Chap2.decoupled.MessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class StdOutMessageRenderer implements MessageRenderer {
    private MessageProvider messageProvider;
    
    @Override
    public MessageProvider getMessageProvider() {
        return this.messageProvider;
    }

    @Override
    @Autowired
    public void setMessageProvider(MessageProvider provider) {
        this.messageProvider = provider;
    }

    @Override
    public void render() {
        System.out.println(messageProvider.getMessage());
    }


}
