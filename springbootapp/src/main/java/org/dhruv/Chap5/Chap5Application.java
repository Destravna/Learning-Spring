package org.dhruv.Chap5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import Chap5.UsingAspectJStyle.NewDocumentarist;

@Configuration
@SpringBootApplication
public class Chap5Application {
    public static void main(String[] args) throws Exception {
        var ctx = SpringApplication.run(Chap5Application.class, args);
       
    }
}
