package org.dhruv;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.dhruv.Chap2.decoupled.MessageProvider;
import org.dhruv.Chap2.decoupled.MessageRenderer;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import Chapter4.integrationTestingSomething.provider.ProviderConfig;
import Chapter4.integrationTestingSomething.renderer.RendererConfig;

public class MessageRendererIT {

    @Test
    void testConfig(){
        var ctx = new AnnotationConfigApplicationContext(RendererConfig.class, ProviderConfig.class);
        var messageProvider = ctx.getBean(MessageProvider.class);
        var messageRenderer = ctx.getBean(MessageRenderer.class);

        assertAll("MESSAGE-TEST", 
            () -> assertNotNull(messageProvider),
            () -> assertNotNull(messageRenderer),
            () -> assertEquals(messageProvider, messageRenderer.getMessageProvider())
        );

        ctx.close();
    }

}
