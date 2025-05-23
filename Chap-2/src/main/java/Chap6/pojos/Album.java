package Chap6.pojos;

import java.io.Serializable;
import java.time.LocalDate;

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
}
