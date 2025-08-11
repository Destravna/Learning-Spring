package Chap10.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import Chap7.entities.Singer;

public interface SingerRepository extends CrudRepository<Singer, Long> {
    Iterable<Singer> findByFirstName(String firstName);

    Iterable<Singer> findByFirstNameAndLastName(String firstName, String lastName);

    @Modifying(clearAutomatically = true)
    @Query("update Singer s set s.firstName = :firstName where s.id = :id")
    int setFirstNameFor(@Param("firstName")String firstName, @Param("id") Long id);

    @Query(value = "select first_name as firstName, last_name as lastName from SINGER where id = :id", nativeQuery = true)
    Object[] getFullNameById(@Param("id")Long id);



    // In the below the header of the column should match with the function name - get otherwise spring won't be able to read. 
    
    @Query(value = "select first_name as firstName, last_name as lastName from SINGER where id = :id", nativeQuery = true)
    Optional<FullName> getFullNameByIdInt(@Param("id") Long id);

    public interface FullName {
        String getFirstName();
        String getLastName();

        default String getFullName() {
            return getFirstName().concat(" ").concat(getLastName());
        }
        
    }


}
