package org.dhruv;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.swing.Renderer;

import org.dhruv.Chap2.decoupled.MessageProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import Chapter4.integrationTestingSomething.provider.ProviderConfig;
import Chapter4.integrationTestingSomething.renderer.RendererConfig;

public class IntegrationTestWithSharedContext {

    private static ApplicationContext ctx;

    @BeforeAll
    private static void setup(){
        ctx = new AnnotationConfigApplicationContext(RendererConfig.class, ProviderConfig.class);
    }

    @Test
    void testProvider(){
        var messageProvider = ctx.getBean(MessageProvider.class);
        assertNotNull(messageProvider);
    }

    


}
