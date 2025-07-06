package Chap6.config.springJDBCmodeling;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Chap6.config.BasicDataSourceConfig;
import Chap6.pojos.Album;
import Chap6.pojos.Singer;



@Configuration
@ComponentScan
public class JdbcModelingDemo {
    private static final Logger logger = LoggerFactory.getLogger(JdbcModelingDemo.class);
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BasicDataSourceConfig.class, JdbcModelingDemo.class);
        SingerRepoImpl singerRepoImpl = ctx.getBean(SingerRepoImpl.class);
        
        // singerRepoImpl.insert(getNewSinger());
        // Singer newSingerWithAlbum = getNewSinger();
        // newSingerWithAlbum.setAlbums(new HashSet<>());
        // newSingerWithAlbum.getAlbums().add(getNewAlbum());
        // singerRepoImpl.insertWithAlbum(newSingerWithAlbum);



        List<Singer> singers = singerRepoImpl.findAll();
        singers.forEach(System.out::println);
        // Singer randomlyUpdateThisSinger = singers.get(0);
        // singerRepoImpl.update(randomlyUpdateThisSinger);
        List<Singer> edNamedSingers = singerRepoImpl.findByFirstName("Ed");
        edNamedSingers.forEach(System.out::println);

        String firstNameUsingId = singerRepoImpl.findFirstNameById(10L).orElse(null);
        logger.info("name found for id 10 {}",  firstNameUsingId);      

    }  

    public static Singer getNewSinger(){
        Singer singer = new Singer();
        singer.setFirstName("imagine");
        singer.setLastName("dinosaurs");
        singer.setBirthDate(LocalDate.now());
        return singer;
    }

    public static Album getNewAlbum(){
        Album album = new Album();
        album.setTitle("code with me");
        album.setReleaseDate(LocalDate.now().minusYears(10));
        return album;
    }


}
