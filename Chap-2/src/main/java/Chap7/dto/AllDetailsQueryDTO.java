package Chap7.dto;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllDetailsQueryDTO {
    private String firstName;
    private String lastName;
    private String title;
    private LocalDate releaseDate;
    private String instruemtnId;

    @Override
    public String toString() {
        return new ToStringBuilder(AllDetailsQueryDTO.class)
                        .append("firstName", firstName)
                        .append("lastName", lastName)
                        .append("title", title)
                        .append("releaseDate", releaseDate)
                        .append("instrumentId", instruemtnId)
                        .toString();
    }
}
