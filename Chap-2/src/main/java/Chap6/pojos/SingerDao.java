package Chap6.pojos;

import java.util.Optional;
import java.util.Set;

import Chap6.dao.CoreDao;

public interface SingerDao extends CoreDao {
    Set<Singer> findAll();

    Set<Singer> findByFirstName(String firstName);

    Optional<String> findNameById(Long id);

    Optional<String> findLastNameById(Long id);

    Optional<String> findFirstNameById(Long id);

    Singer insert(Singer singer);

    void update(Singer singer);

    void delete(Long singerId);

    Set<Singer> findAllWithAlbums();

    void insertWithAlbum(Singer singer);

}
