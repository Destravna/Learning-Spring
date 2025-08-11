package Chap7.entities;

import java.io.Serial;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;


@Entity
@Table(name = "SINGER")
@SqlResultSetMapping(
    name = "singerResult" ,
    entities = @EntityResult(entityClass = Singer.class)
)
@NamedQueries({
    @NamedQuery(
        name = "Singer.findAllWithAlbum",
        query = "SELECT DISTINCT s FROM Singer s LEFT JOIN FETCH s.albums LEFT JOIN FETCH s.instruments"
    ),
    @NamedQuery(
        name = "Singer.findById",
        query = "SELECT DISTINCT s FROM Singer s LEFT JOIN FETCH s.albums LEFT JOIN FETCH s.instruments i where s.id = :id"
    ),
    @NamedQuery(
        name = "Singer.findAll",
        query = "SELECT DISTINCT s FROM Singer s"
    )
})
@NamedNativeQueries({
    @NamedNativeQuery(
        name = "Singer.getFirstNameById(?)",
        query = "select getfirstnamebyid(:id)"
    )
})
public class Singer extends AbstractEntity {
    public static final String FIND_ALL_WITH_ALBUM = "Singer.findAllWithAlbum";

    @Serial 
    private static final long serialVersionUID = 1;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Set<Album> albums = new HashSet<>();
    private Set<Instrument> instruments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "SINGER_INSTRUMENT", joinColumns = @JoinColumn(name = "SINGER_ID"), inverseJoinColumns = @JoinColumn(name = "INSTRUMENT_ID"))
    public Set<Instrument> getInstruments() {
        return instruments;
    }

    @OneToMany(mappedBy = "singer", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    @Column(name = "BIRTH_DATE")
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setInstruments(Set<Instrument> instruments) {
        this.instruments = instruments;
    }

    

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("first_name", firstName).append("last_name", lastName)
                    .append("version", getVersion()).append("birth_date", birthDate).append("id", id).toString();
    }

    

    public void removeAlbum(Album album){
        albums.remove(album);
    }

}
