package Chap6.plain;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Chap6.pojos.Singer;
import Chap6.pojos.SingerDao;

public class PlainJdbcDemo {
    private static final Logger logger = LoggerFactory.getLogger(PlainJdbcDemo.class);
    private static final SingerDao singerDao = new PlainSingerDao();
    public static void main(String[] args) {
        try{
            Class.forName("org.mariadb.jdbc.Driver");
        } 
        catch (ClassNotFoundException ex){
            logger.error("Problem loading DB driver", ex);
        }
        singerDao.delete(4L);
        listAllSingers();
        Singer singer = new Singer();
        singer.setFirstName("Ed");
        singer.setLastName("Sheeran");
        singer.setBirthDate(LocalDate.of(1991, 2, 17));

        singerDao.insert(singer);
        logger.info("singer id: {}", singer.getId());
        listAllSingers();
        

    }


    private static void listAllSingers(){
        var singers = singerDao.findAll();
        for(Singer singer: singers){
            System.out.println(singer);
        }
    }

}
