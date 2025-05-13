package Chap3.CollectionInjection;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import Chap3.nesting.Song;

@Configuration
public class CollectionConfig {

    @Bean
    public List<Song> list(){
        return List.of(new Song("Not the end"), new Song("Rise up"));
    }

    @Bean
    public Song song1(){
        return new Song("here's to hoping on meth");
    }

    @Bean
    public Song song2(){
        return new Song("wishing the best for you");
    }


}
