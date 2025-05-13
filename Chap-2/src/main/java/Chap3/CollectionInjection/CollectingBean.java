package Chap3.CollectionInjection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import Chap3.nesting.Song;

public class CollectingBean {

    @Autowired
    List<Song> songList;

    @Autowired
    @Qualifier("list")
    List<Song> songList2;

    public void printCollections(){
        songList.forEach(song -> System.out.println(song.getTitle()));
    }

    public void printCollections2(){
        System.out.println("printing the songs from the list:");
        songList2.forEach(song -> System.out.println(song.getTitle()));
    }
}
