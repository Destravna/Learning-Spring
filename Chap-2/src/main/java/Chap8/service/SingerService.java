package Chap8.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import Chap7.entities.Singer;

public interface SingerService {
    String ALL_SINGER_NATIVE_QUERY = "select ID, FIRST_NAME, LAST_NAME, BIRTH_DATE, VERSION from SINGER";
    
    Stream<Singer> findAll();
    Stream<Singer> findAllWithAlbum();
    Optional<Singer> findById(Long id);
    void save(Singer singer);
    void delete(Singer singer);
    Stream<Singer> findAllByNativeQuery();
    List<String> findFirstNames();
    String findFirstNameById(Long id);
    Stream<Singer> findByCriteriaQuery(String firstName, String lastName);
}
