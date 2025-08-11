package Chap7;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import Chap7.config.HibernateConfig;
import Chap7.dao.InstrumentDao;
import Chap7.dao.SingerDao;
import Chap7.entities.Album;
import Chap7.entities.Instrument;
import Chap7.entities.Singer;

public class HibernateDemo {
    private static final Logger logger = LoggerFactory.getLogger(HibernateDemo.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(HibernateConfig.class);
        var singerDao = ctx.getBean(SingerDao.class);
        var instrumentDao = ctx.getBean(InstrumentDao.class);
        logger.info("listing singers");
        singerDao.findAll().forEach(singer -> logger.info(singer.toString()));
        logger.info("________________________________________________________________");
        listSingersWithAlbum(singerDao.findAllWithAlbum());
        logger.info("__find by id__");
        Singer singer = singerDao.findByid(1L);
        logger.info("found by id : {}", singer.toString());
        Singer dhruv = getSingerWithAlbum();
        // Singer newSinger = singerDao.saveWithInstrument(dhruv);
        // logger.info("New singer id : {}", newSinger.getId());

        // instrumentDao.save(getMockInstrument());
        // singerDao.delete(singerDao.findByid(14L));
        // singerDao.findAll().forEach(s -> logger.info(s.toString()));
        Singer allDetails = singerDao.findAllDetails("John", "Doe");
        logger.info("all details: {}", allDetails);
        singerDao.findAllNamesByProjection();
        String nameUsingId = singerDao.firstNameById(1L);
        logger.info("running sql funciton : {}",  nameUsingId);
        ctx.close();
       
    }

    private static void listSingersWithAlbum(List<Singer> singers) {
        logger.info("Listing singers with albums");
        singers.forEach(s -> {
            logger.info(s.toString());
            if (s.getAlbums() != null) {
                s.getAlbums().forEach(a -> logger.info("\t" + a.toString()));
            }
            if (s.getInstruments() != null) {
                s.getInstruments().forEach(i -> logger.info(i.toString()));
            }
        });
    }

    public static Singer getSingerWithAlbum() {
        Singer dhruv = new Singer();
        dhruv.setLastName("singh");
        dhruv.setFirstName("dihruv");
        dhruv.setBirthDate(LocalDate.parse("2003-01-19"));
        Album album = new Album();
        album.setTitle("A heart full of cheesecake");
        album.setReleaseDate(LocalDate.of(2025, 2, 14));
        album.setSinger(dhruv);
        dhruv.getAlbums().add(album);
        // Instrument instrument = new Instrument();
        // instrument.setInstrumentId("violin");
        // dhruv.getInstruments().add(instrument);
        // instrument.getSingers().add(dhruv); //don't make double references before
        // saving.
        return dhruv;
    }

    private static Instrument getMockInstrument() {
        Instrument instrument = new Instrument();
        instrument.setInstrumentId("flute");
        return instrument;
    }

}
