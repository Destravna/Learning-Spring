package org.dhruv.springbootapp;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootappApplication {

	private static Logger log = LoggerFactory.getLogger(SpringbootappApplication.class);
	public static void main(String[] args) {
		
		var ctx = SpringApplication.run(SpringbootappApplication.class, args);
		// assert(ctx != null);
		// Arrays.stream(ctx.getBeanDefinitionNames()).forEach(log::info);
	}

}
