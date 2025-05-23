package Chap6.pojos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Singer implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Set<Album> albums;
    
    @Override
    public String toString() {
        return new ToStringBuilder("singer").append("id", id).append("name", firstName + " " + lastName).append("birthDate",  birthDate).toString();
    }


}
