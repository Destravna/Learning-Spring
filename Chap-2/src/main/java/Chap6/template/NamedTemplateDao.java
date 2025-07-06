package Chap6.template;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import Chap6.dao.SingerDao;
import Chap6.pojos.Singer;

public class NamedTemplateDao implements SingerDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<String> findNameById(Long id) {
        try {
            String fullName = namedParameterJdbcTemplate.queryForObject(
                    "SELECT CONCAT(first_name, ' ', last_name) FROM SINGER WHERE id = :singerId",
                    Map.of("singerId", id),
                    String.class);
            return Optional.ofNullable(fullName);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Set<Singer> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Singer> findAllWithAlbum() {
        throw new UnsupportedOperationException();

    }

}
