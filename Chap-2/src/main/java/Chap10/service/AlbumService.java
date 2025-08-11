package Chap10.service;

import java.time.LocalDate;
import java.util.stream.Stream;


import Chap7.entities.Album;
import Chap7.entities.Singer;

public interface AlbumService {
    public Stream<Album> findBySinger(Singer singer);
    public Stream<Album> findWithReleaseDateGreaterThan(LocalDate date);
    public Stream<Album> findWithXInTitle(String x);
}
