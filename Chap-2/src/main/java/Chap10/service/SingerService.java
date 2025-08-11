package Chap10.service;

import java.util.Optional;
import java.util.stream.Stream;

import Chap10.repository.SingerRepository.FullName;
import Chap7.entities.Singer;

public interface SingerService {
    Stream<Singer> findAll();
    Stream<Singer> findByFirstName(String firstName);
    Stream<Singer> findByFirstNameAndLastName(String firstName, String lastName);
    Singer updateFirstName(String firstName, Long id);
    Object[] getFullNameById(Long id);
    Optional<FullName> getFullNameByIdInt(Long id);
}
