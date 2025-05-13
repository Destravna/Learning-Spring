package Chapter4.profilesDemo;


import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;


public class EnvironmentTest {
    private static Logger logger = LoggerFactory.getLogger(EnvironmentTest.class);
    
    @Test
    void testPropertySource(){
        var ctx = new GenericApplicationContext();

        ConfigurableEnvironment env = ctx.getEnvironment();
        MutablePropertySources propertySources = env.getPropertySources();

        Map<String,Object> appMap = new HashMap<>();
        appMap.put("user.home", "CUSTOM_USER_HOME");
        propertySources.addLast(new MapPropertySource("prospring6_MAP", appMap));
        
        logger.info("--Env Variables from java.lang.system -- ");
        logger.info("user.home: " + System.getProperty("user.home"));
        logger.info("JAVA_HOME: " + System.getenv("JAVA_HOME"));
        logger.info("-- Env Variables  from ConfigurableEnvironment --");
        logger.info("user.home: " + env.getProperty("user.home"));
        logger.info("JAVA_HOME: " + env.getProperty("JAVA_HOME"));

        ctx.close();
    }

     
    

    
}
