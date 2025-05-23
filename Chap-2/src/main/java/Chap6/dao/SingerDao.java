package Chap6.dao;

import java.util.Optional;

public interface SingerDao {
    Optional<String> findNameById(Long id);
}
