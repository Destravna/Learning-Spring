package Chap9.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import Chap7.entities.Album;
import Chap7.entities.Singer;
import Chap9.exception.TitleTooLongException;

public interface AllService {
    Optional<Singer> findByIdWithAlbums(Long id);
    Stream<Singer> findAllWithAlbums();
    void update(Singer singer);
    Long countSingers();
    void saveSingerWithAlbums(Singer s, Set<Album> albums) throws TitleTooLongException;
    // Long countSingers();
}
