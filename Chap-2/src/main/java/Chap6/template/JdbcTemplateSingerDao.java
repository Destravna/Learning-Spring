package Chap6.template;

import java.util.Optional;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;

import Chap6.dao.SingerDao;
import Chap6.pojos.Singer;


public class JdbcTemplateSingerDao implements SingerDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public Optional<String> findNameById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select CONCAT(first_name, ' ', last_name) from SINGER where id = ?", String.class, id));
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
