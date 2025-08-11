package Chap10.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Chap7.entities.Album;
import Chap7.entities.Singer;

public interface AlbumRepo extends JpaRepository<Album, Long> {
    Iterable<Album> findBySinger(Singer singer);
    Iterable<Album> findWithReleaseDateGreaterThan(LocalDate releasDate);
    
    @Query("select a from Album a where a.title like %:title% ")
    Iterable<Album> findByTitle(@Param("title") String t);


}
