package org.dhruv;

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

        // THESE ARE THE PROPERTIES
        appMap.put("user.home", "CUSTOM_USER_HOME");
        appMap.put("prospring6_MAP", "Dhruv Singh did this?");

        //THIS HERE IN NEW MAPPR----- IS JUST THE NAME OF THE PROPERTY SOURCE
        propertySources.addLast(new MapPropertySource("prospring6_MAP", appMap)); //ADD LAST MEANS, FIRST LOOKS FOR PROPERTY IN SYSTEM ,RESOURCE FILES, ETC, THEN IN THIS PROPERTYSOURCE
        
        logger.info("--Env Variables from java.lang.system -- ");
        logger.info("user.home: " + System.getProperty("user.home"));
        logger.info("JAVA_HOME: " + System.getenv("JAVA_HOME"));
        logger.info("-- Env Variables  from ConfigurableEnvironment --");
        logger.info("user.home: " + env.getProperty("user.home"));
        logger.info("JAVA_HOME: " + env.getProperty("JAVA_HOME"));
        logger.info("prospring6_MAP: {}", env.getProperty("prospring6_MAP"));

        ctx.close();
    }

     
    

    
}
