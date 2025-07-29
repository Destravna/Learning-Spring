package Chap8;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import Chap7.entities.Album;
import Chap8.config.JpaConfig;
import Chap8.service.SingerService;
import Chap8.service.SingerSummaryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaDemo {
    private static final Logger logger = LoggerFactory.getLogger(JpaDemo.class);
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(JpaConfig.class);
        SingerService singerService = ctx.getBean(SingerService.class);
        SingerSummaryService singerSummaryService = ctx.getBean(SingerSummaryService.class);

        var allSingers = singerService.findAll();
        allSingers.forEach(s -> logger.info("singer : {}", s));
        logger.info("Finding all with album");
        var allSingersWithAlbum = singerService.findAllWithAlbum();
        allSingersWithAlbum.forEach(s -> {
            logger.info(s.toString());
            for(Album a:s.getAlbums()){
                logger.info(" {} {} :: album {}", s.getFirstName(),  s.getLastName(), a);
            }
        });
        // logger.info("_____________singer summary service logging_____________");
        // var recordSingers = singerSummaryService.findAllAsRecord().toList();
        // recordSingers.forEach(record -> logger.info("record :: {}", record.toString()));
        logger.info("the native query thing");
        var result = singerService.findFirstNames();
        logger.info(result.toString());

        var singersByNative = singerService.findAllByNativeQuery().toList();
        singersByNative.forEach(System.out::println);

        var firstNameOf3L = singerService.findFirstNameById(3L);
        logger.info("first name fo 3L : {}", firstNameOf3L);
        
        ctx.close();
        

    }
}
