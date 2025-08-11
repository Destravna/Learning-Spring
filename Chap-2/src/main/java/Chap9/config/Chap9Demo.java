package Chap9.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import Chap7.entities.Album;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Chap9.repos.AlbumRepo;
import Chap9.repos.SingerRepo;
import Chap9.services.AllService;



public class Chap9Demo {
    private static final Logger logger = LoggerFactory.getLogger(Chap9Demo.class);
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(TransactionCfg.class);
        SingerRepo singerRepo = ctx.getBean(SingerRepo.class);
        AlbumRepo albumRepo = ctx.getBean(AlbumRepo.class);
        AllService allService = ctx.getBean(AllService.class);
        assertNotNull(singerRepo);
        var singer = singerRepo.findById(1L);
        // logger.info(singer.toString());
        var albums = albumRepo.findBySinger(singer.get());
        // albums.forEach(a -> logger.info(a.toString()));
        // singerRepo.findAll().forEach(s -> logger.info(s.toString()));
        allService.findAllWithAlbums().forEach(s -> {
            logger.info("singer : {}", s.toString());
            for(Album a: s.getAlbums()){
                logger.info("\t-> {}",  a.toString());
            }
        });
        ctx.close();
        
    }

}
