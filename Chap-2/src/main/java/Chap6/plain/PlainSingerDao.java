package Chap6.plain;

import Chap6.pojos.Singer;
import Chap6.pojos.SingerDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlainSingerDao implements SingerDao {
    private static final Logger logger = LoggerFactory.getLogger(PlainSingerDao.class);

    @Override
    public Set<Singer> findAll() {
        Set<Singer> result = new HashSet<>();
        try (
                var connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("select * from SINGER");
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                var singer = new Singer();
                singer.setId(resultSet.getLong("id"));
                singer.setFirstName(resultSet.getString("first_name"));
                singer.setLastName(resultSet.getString("last_name"));
                singer.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
                result.add(singer);
            }
        } catch (Exception ex) {
            logger.error("Problem when executing SELECT!", ex);
        }
        return result;

    }

    @Override
    public Singer insert(Singer singer) {
        try (var connection = getConnection()) {
            var statement = connection.prepareStatement(
                    "insert into SINGER (first_name, last_name, birth_date) values (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, singer.getFirstName());
            statement.setString(2, singer.getLastName());
            statement.setDate(3, java.sql.Date.valueOf(singer.getBirthDate()));
            statement.execute();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                singer.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException ex) {
            logger.error("erro in inserting", ex);
        }
        return singer;
    }

    @Override
    public void delete(Long singerId) {
        try (var connection = getConnection();
                var statement = connection.prepareStatement("delete from SINGER where id = ?")) {
            statement.setLong(1, singerId);
            statement.execute();
        } catch (SQLException ex) {
            logger.error("Problem executing DELETE", ex);
        }
    }

    @Override
    public Set<Singer> findByFirstName(String firstName) {
        throw new NotImplementedException("findByFirstName");
    }

    @Override
    public Optional<String> findNameById(Long id) {
        throw new NotImplementedException("findNameById");
    }

    @Override
    public Optional<String> findLastNameById(Long id) {
        throw new NotImplementedException("findLastNameById");
    }

    @Override
    public Optional<String> findFirstNameById(Long id) {
        throw new NotImplementedException("findFirstNameById");
    }

    @Override
    public void update(Singer singer) {
        throw new NotImplementedException("update");
    }

    @Override
    public Set<Singer> findAllWithAlbums() {
        throw new NotImplementedException("findAllWithAlbums");
    }

    @Override
    public void insertWithAlbum(Singer singer) {
        throw new NotImplementedException("insertWithAlbum");
    }

}
