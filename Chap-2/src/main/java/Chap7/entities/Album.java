package Chap7.entities;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "ALBUM")
@NamedQueries({
    @NamedQuery(name = "Album.findBySinger", query = "select distinct a from Album a where a.singer = :singer"),
    @NamedQuery(name=Album.FIND_ALL_WITH_RELEASE_DATE_GREATER_THAN, query="select a from Album a where a.releaseDate > ?1")
})
public class Album extends AbstractEntity {
    private static final String FIND_ALL_WITH_RELEASE_DATE_GREATER_THAN = "Album.findWithReleaseDateGreaterThan";
    @Serial private static final long serialVersionUID = 3L;

    private String title;
    private LocalDate releaseDate;
    private Singer singer;

    @ManyToOne
    @JoinColumn(name = "SINGER_ID")
    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    @Column
    public String getTitle() {
        return title;
    }

    @Column(name = "release_date")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(LocalDate releasDate) {
        this.releaseDate = releasDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("title", title).append("id", id).append("release_date", releaseDate).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, releaseDate);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        if(!super.equals(obj)) return false;
        Album album = (Album) obj;
        if(album != null && album.getId() == this.getId()) return true;
        return false;
    }

}
