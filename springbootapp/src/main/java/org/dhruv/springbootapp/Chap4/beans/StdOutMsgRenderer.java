package org.dhruv.springbootapp.Chap4.beans;

import org.dhruv.Chap2.decoupled.MessageProvider;
import org.dhruv.Chap2.decoupled.MessageRenderer;
import org.junit.jupiter.api.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Order(2)
@Component("msg-renderer")
public class StdOutMsgRenderer implements MessageRenderer , CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StdOutMsgRenderer.class);
    private MessageProvider messageProvider;

    @Override
    public MessageProvider getMessageProvider() {
        return messageProvider;
    }
    
    @Override
    public void render() {
        logger.info(getMessageProvider().getMessage());
    }

    @Autowired
    @Override
    public void setMessageProvider(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Running run");
        render();
    }
}
