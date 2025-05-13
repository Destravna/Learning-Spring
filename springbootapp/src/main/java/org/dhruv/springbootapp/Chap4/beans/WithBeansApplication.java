package org.dhruv.springbootapp.Chap4.beans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.dhruv.Chap2.decoupled.MessageRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
public class WithBeansApplication {
    private static final Logger logger = LoggerFactory.getLogger(WithBeansApplication.class);
    public static void main(String[] args) {
        logger.info("args length : {}", args.length);
        var ctx = SpringApplication.run(WithBeansApplication.class, args);
        // MessageRenderer mr = ctx.getBean(MessageRenderer.class);
        // mr.render();
    }
}
