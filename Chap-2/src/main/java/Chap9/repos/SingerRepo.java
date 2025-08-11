package Chap9.repos;

import java.util.Optional;
import java.util.stream.Stream;

import Chap7.entities.Singer;

public interface SingerRepo {
    Stream<Singer> findAll();
    Optional<Singer> findById(Long id);
    Singer save(Singer singer);
    Long countAllSingers();
}
