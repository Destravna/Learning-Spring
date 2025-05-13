package org.dhruv.springbootapp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.dhruv.Chap2.decoupled.MessageProvider;
import org.dhruv.Chap2.decoupled.MessageRenderer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class BeansTest {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MessageRenderer messageRenderer;

    @Autowired
    MessageProvider messageProvider;

    @Test
    void contextLoad(){
        assertNotNull(applicationContext);
    }

    @Test
    void renderTest(){
        assertAll("messageTest", 
        () -> assertNotNull(messageProvider),
        () -> assertNotNull(messageRenderer),
        () -> assertEquals(messageProvider, messageRenderer.getMessageProvider())
        );
    }

}
