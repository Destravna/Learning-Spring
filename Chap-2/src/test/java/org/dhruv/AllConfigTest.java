package org.dhruv;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.dhruv.Chap2.decoupled.MessageProvider;
import org.dhruv.Chap2.decoupled.MessageRenderer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import Chapter4.allconfig.AllConfig;

@Configuration
class TestConfig {
    @Profile("test")
    @Bean
    MessageProvider messageProvider() {
        return new MessageProvider() {
            @Override
            public String getMessage() {
                return "TEST MESSAGE";
            }
        };
    }
}


@ActiveProfiles("test")
@SpringJUnitConfig(classes = {AllConfig.class, TestConfig.class})
public class AllConfigTest {
    @Autowired
    MessageRenderer renderer;
    @Autowired
    MessageProvider provider;

    @Test
    void testConfig(){
        assertAll("test-message", 
            () -> assertNotNull(provider),
            () -> assertNotNull(renderer),
            () -> assertEquals(renderer.getMessageProvider(), provider)
        );

        renderer.render();

    }
}
