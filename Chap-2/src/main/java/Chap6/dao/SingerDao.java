package Chap6.dao;

import java.util.Optional;
import java.util.Set;

import Chap6.pojos.Singer;

public interface SingerDao {
    Optional<String> findNameById(Long id);
    Set<Singer> findAll();
    Set<Singer> findAllWithAlbum();
}
