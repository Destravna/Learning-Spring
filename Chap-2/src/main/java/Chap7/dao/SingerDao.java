package Chap7.dao;

import java.util.List;
import java.util.Set;

import Chap7.entities.Singer;

public interface SingerDao {
    List<Singer> findAll();
    List<Singer> findAllWithAlbum();
    Singer findByid(Long id);
    Singer save(Singer singer);
    Singer saveWithInstrument(Singer singer);
    void delete(Singer singer);
    Singer findAllDetails(String firstName, String lastName);
    Set<String> findAllNamesByProjection();
    String firstNameById(Long id);

}
