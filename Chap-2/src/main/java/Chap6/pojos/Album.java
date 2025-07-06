package Chap6.pojos;

import java.io.Serializable;
import java.time.LocalDate;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Album implements Serializable {
    private Long id;
    private Long singerId;
    private String title;
    private LocalDate releaseDate;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("title", title).append("releaseDate", releaseDate.toString()).toString();
    }
}
