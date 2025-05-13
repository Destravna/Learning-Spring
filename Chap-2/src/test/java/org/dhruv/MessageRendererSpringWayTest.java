package org.dhruv;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.dhruv.Chap2.decoupled.MessageProvider;
import org.dhruv.Chap2.decoupled.MessageRenderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import Chapter4.integrationTestingSomething.provider.ProviderConfig;
import Chapter4.integrationTestingSomething.renderer.RendererConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RendererConfig.class, ProviderConfig.class })
public class MessageRendererSpringWayTest {

    @Autowired
    MessageProvider provider;

    @Autowired
    MessageRenderer renderer;

    @Test
    void testProvider() {
        assertNotNull(provider);
    }

    @Test
    void testRenderer(){
        assertAll("renderer-tes", 
            () -> assertNotNull(renderer),
            () -> assertNotNull(renderer.getMessageProvider()),
            () -> assertEquals(provider, renderer.getMessageProvider())
        );

        renderer.render();
    }

}
