package Chap9.repos;

import java.util.Set;
import java.util.stream.Stream;

import Chap7.entities.Album;
import Chap7.entities.Singer;
import Chap9.exception.TitleTooLongException;

public interface AlbumRepo {
    Stream<Album> findBySinger(Singer singer);
    Set<Album> save(Set<Album> albums) throws TitleTooLongException;
    Album save(Album album) throws TitleTooLongException;
}
