package org.dhruv;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.dhruv.Chap2.decoupled.MessageProvider;
import org.dhruv.Chap2.decoupled.MessageRenderer;
import org.dhruv.Chap2.decoupled.StdOutMessageRenderer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageRendererTest {

    private static final Logger logger = LoggerFactory.getLogger(MessageRendererTest.class);

    @Test
    void testStandardOutMessageRenderer(){
        logger.info("Testing the Standard Out message renderer");
        MessageProvider mockProvider = mock(MessageProvider.class);
        when(mockProvider.getMessage()).thenReturn("test message");
        MessageRenderer messageRenderer = new StdOutMessageRenderer();
        messageRenderer.setMessageProvider(mockProvider);

        messageRenderer.render();
        // verifies that he message was called exactly onece
        verify(mockProvider, times(1)).getMessage();


    }

}
