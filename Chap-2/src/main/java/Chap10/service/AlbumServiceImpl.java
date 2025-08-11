package Chap10.service;

import java.time.LocalDate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Chap10.repository.AlbumRepo;
import Chap7.entities.Album;
import Chap7.entities.Singer;


@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumRepo albumRepository;

    @Override
    public Stream<Album> findBySinger(Singer singer) {
        return StreamSupport.stream(albumRepository.findBySinger(singer).spliterator(), false);
    }

    @Override
    public Stream<Album> findWithReleaseDateGreaterThan(LocalDate date) {
        return StreamSupport.stream(albumRepository.findWithReleaseDateGreaterThan(date).spliterator(), false);
    }

    @Override
    public Stream<Album> findWithXInTitle(String x) {
        return StreamSupport.stream(albumRepository.findByTitle(x).spliterator(), false);
    }

}
