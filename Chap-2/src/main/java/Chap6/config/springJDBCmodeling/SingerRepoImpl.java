package Chap6.config.springJDBCmodeling;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Chap6.pojos.Album;
import Chap6.pojos.Singer;

@Repository
public class SingerRepoImpl implements SingerRepo{
    private static final Logger logger = LoggerFactory.getLogger(SingerRepoImpl.class);
    private DataSource dataSource;
    private SelectAllSingers selectAllSingers;
    @Autowired private SelectSingerByFirstName selectSingerByFirstName;
    @Autowired private UpdateSinger updateSinger;
    @Autowired private InsertSinger insertSinger;
    @Autowired private InsertSingerAlbum insertSingerAlbum;
    @Autowired private StoredFunctionFirstNameById storedFunctionFirstNameById;
    @Autowired private StoredFunctionIdByFirstAndLastName storedFunctionIdByFirstAndLastName;


    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void setSelectAllSingers(SelectAllSingers selectAllSingers) {
        this.selectAllSingers = selectAllSingers;
    }
    
    @Override
    public List<Singer> findAll() {
        return selectAllSingers.execute();
        
    }

    public List<Singer> findByFirstName(String firstName){
        return selectSingerByFirstName.executeByNamedParam(Map.of("first_name", firstName));
    }

    public void update(Singer singer){
        updateSinger.updateByNamedParam(Map.of(
            "first_name", singer.getFirstName() + "updated",
            "last_name", singer.getLastName() + "updatedSingh",
            "birth_date", singer.getBirthDate(),
            "id", singer.getId()
        ));
        logger.info("updated singer : {}", singer);
    }

    public void insert(Singer singer){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertSinger.updateByNamedParam(Map.of("first_name", singer.getFirstName(), "last_name", singer.getLastName(), "birth_date", singer.getBirthDate()), keyHolder);
        logger.info("New singer {} generated with id {}", singer, keyHolder.getKey().longValue());
    }

    public void insertWithAlbum(Singer singer){
        var keyHolder = new GeneratedKeyHolder();
        insertSinger.updateByNamedParam(Map.of("first_name", singer.getFirstName(), "last_name", singer.getLastName(), "birth_date", singer.getBirthDate()), keyHolder);
        logger.info("new singer {} inserted with id {}", singer, keyHolder.getKey().longValue());

        var albums = singer.getAlbums();
        if(albums != null){
            for(Album album :albums){
                insertSingerAlbum.updateByNamedParam(Map.of("singer_id", keyHolder.getKey().longValue(), "title", album.getTitle(), "release_date", album.getReleaseDate()));
            }
            insertSingerAlbum.flush();
        }
    }

    public Optional<String> findFirstNameById(Long id){
        var firstName = storedFunctionFirstNameById.execute(id).get(0);
        return Optional.ofNullable(firstName); 
    }

    public Optional<Long> findIdByFirstNameAndLastName(String firstName, String lastName){
        Long id = storedFunctionIdByFirstAndLastName.execute(firstName, lastName).get(0);
        return Optional.ofNullable(id);
    }




    
}
