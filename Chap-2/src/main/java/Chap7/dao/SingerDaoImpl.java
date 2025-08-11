package Chap7.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Chap7.dto.AllDetailsQueryDTO;
import Chap7.entities.Album;
import Chap7.entities.Instrument;
import Chap7.entities.Singer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Transactional
// @Repository("singerDao")
@Repository
public class SingerDaoImpl implements SingerDao {


    private static final String ALL_SELECT = """
                    select DISTINCT s.first_name, s.last_name, a.title, a.RELEASE_DATE, i.INSTRUMENT_ID
                    from SINGER s
                    inner join ALBUM a on s.id = a.singer_id
                    inner join SINGER_INSTRUMENT si on s.ID = si.SINGER_ID
                    inner join INSTRUMENT i on si.INSTRUMENT_ID = i.INSTRUMENT_ID
                    where s.FIRST_NAME = :firstName and s.LAST_NAME= :lastName
                """;
    private static final Logger logger = LoggerFactory.getLogger(SingerDaoImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    private InstrumentDao instrumentDao;

    public SingerDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Singer> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from Singer s", Singer.class).list();                                                                                                 // deprecated/
    }

    @Transactional(readOnly = true)
    @Override
    public List<Singer> findAllWithAlbum() {
        return sessionFactory.getCurrentSession().createNamedQuery("Singer.findAllWithAlbum", Singer.class).list();
    }

    @Transactional(readOnly = true)
    @Override
    public Singer findByid(Long id) {
        return sessionFactory.getCurrentSession().createNamedQuery("Singer.findById", Singer.class)
                .setParameter("id", id).uniqueResult();
    }

    @Override
    public Singer save(Singer singer) {
        singer = sessionFactory.getCurrentSession().merge(singer);
        return singer;
    }

    @Override
    @Transactional
    public Singer saveWithInstrument(Singer singer) {
        Set<Instrument> instruments = singer.getInstruments();
        Set<Instrument> managedInstruments = new HashSet<>();
        instruments.forEach(instrument -> {
            instrument = instrumentDao.save(instrument);
            managedInstruments.add(instrument);
        });
        singer.setInstruments(managedInstruments);
        singer = sessionFactory.getCurrentSession().merge(singer);
        return singer;
    }

    @Override
    public void delete(Singer singer) {
        sessionFactory.getCurrentSession().remove(singer);
        logger.info("singer with id {} deleted", singer.getId());
    }

    @Override
    public Singer findAllDetails(String firstName, String lastName) {
        List<AllDetailsQueryDTO> results = sessionFactory.getCurrentSession()
                            .createNativeQuery(ALL_SELECT, AllDetailsQueryDTO.class)
                            .setParameter("firstName", firstName)
                            .setParameter("lastName", lastName).list();
        var singer = new Singer();
        for(AllDetailsQueryDTO allDetailsQueryDTO:results){
            logger.info("dto : {}", allDetailsQueryDTO);
            if(singer.getFirstName() == null) singer.setFirstName(allDetailsQueryDTO.getFirstName());
            if(singer.getLastName() == null) singer.setLastName(allDetailsQueryDTO.getLastName());

            var album = new Album() ;
            album.setTitle(allDetailsQueryDTO.getTitle());
            album.setReleaseDate(allDetailsQueryDTO.getReleaseDate());

            var instrument = new Instrument();
            instrument.setInstrumentId(allDetailsQueryDTO.getInstruemtnId());

            singer.getAlbums().add(album);
            singer.getInstruments().add(instrument);
        }

        return singer;
    }

    @Transactional(readOnly = true)
    @Override
    public String firstNameById(Long id) {
        return sessionFactory.getCurrentSession().createNativeQuery("select getfirstnamebyid(?)").setParameter(1, id).getSingleResult().toString();
    }
    

    @Transactional(readOnly = true)
    @Override
    public Set<String> findAllNamesByProjection() {
        CriteriaBuilder cb = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Object []> cq = cb.createQuery(Object[].class);
        Root<Singer> root = cq.from(Singer.class);
        cq.multiselect(root.get("firstName"), root.get("lastName"));
        List<Object[]> result = sessionFactory.getCurrentSession().createQuery(cq).list();
        result.forEach(obj -> System.out.println(obj[0] + " " + obj[1]));

        return result.stream().map(o -> o[0] + " " + o[1]).collect(Collectors.toSet());
    }


}
