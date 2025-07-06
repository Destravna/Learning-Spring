package Chap6.plain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Chap6.config.Queries;
import Chap6.dao.SingerDao;
import Chap6.pojos.Album;
import Chap6.pojos.Singer;

public class RowMapperDao implements SingerDao {
    private static final Logger logger = LoggerFactory.getLogger(RowMapperDao.class);

    private NamedParameterJdbcTemplate namedTemplate;

    public void setNamedTemplate(NamedParameterJdbcTemplate namedTemplate) {
        this.namedTemplate = namedTemplate;
    }

    @Override
    public Set<Singer> findAll() {
        return new HashSet<>(namedTemplate.query("select * from SINGER", new SingerMapper()));
    }

    @Override
    public Optional<String> findNameById(Long id) {
        return Optional.empty();
    }

    @Override
    public Set<Singer> findAllWithAlbum() {
        return namedTemplate.query(Queries.singerWithAlbums,  new SingerWithAlbumsExtractor());
    }

    static class SingerWithAlbumsExtractor implements ResultSetExtractor<Set<Singer>> {
        @Override
        public Set<Singer> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Singer> singerMap = new HashMap<>();
            Set<Singer> singers = new HashSet<>();
            Singer singer;
            while (rs.next()) {
                long singerId = rs.getLong("id");
                if (singerMap.get(singerId) != null) {
                    singer = singerMap.get(singerId);
                } else {
                    singer = new Singer();
                    singer.setId(rs.getLong("id"));
                    singer.setFirstName(rs.getString("first_name"));
                    singer.setLastName(rs.getString("last_name"));
                    singer.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    singer.setAlbums(new HashSet<>());
                    singerMap.put(singerId, singer);
                }
                var albumId = rs.getLong("album_id");

                if(rs.wasNull()){
                    logger.info("album id was null");
                }
                else{
                    Album album = new Album();
                    album.setId(albumId);
                    album.setReleaseDate(rs.getDate("release_date").toLocalDate());
                    album.setTitle(rs.getString("title"));
                    singer.getAlbums().add(album);
                }
            }
            singerMap.forEach((key, value)-> singers.add(value));
            return singers;

        }
    }

    static class SingerMapper implements RowMapper<Singer> {
        @Override
        public Singer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Singer singer = new Singer();
            singer.setId(rs.getLong("id"));
            singer.setFirstName(rs.getString("first_name"));
            singer.setLastName(rs.getString("last_name"));
            singer.setBirthDate(rs.getDate("birth_date").toLocalDate());
            singer.setAlbums(Set.of());
            return singer;
        }
    }

}
