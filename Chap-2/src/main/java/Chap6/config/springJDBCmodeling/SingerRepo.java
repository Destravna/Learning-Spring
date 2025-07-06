package Chap6.config.springJDBCmodeling;

import java.util.List;


import Chap6.pojos.Singer;

public interface SingerRepo {
    List<Singer> findAll();
} 
