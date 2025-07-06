package Chap6.config;

public class Queries {
    public static final String singerWithAlbums = "select s.id, s.first_name, s.last_name, s.birth_date, a.id AS album_id, a.title, a.release_date  from SINGER s  left join ALBUM a on s.id = a.singer_id";

}
