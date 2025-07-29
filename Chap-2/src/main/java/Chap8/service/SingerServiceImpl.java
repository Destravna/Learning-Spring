package Chap8.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import Chap7.entities.Singer;
import Chap7.entities.Singer_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("jpaSingerService")
@Repository
@Transactional
public class SingerServiceImpl implements SingerService {
    private static final Logger logger = LoggerFactory.getLogger(SingerServiceImpl.class);

    @PersistenceContext
    private EntityManager em;
    private static final String findAll = "Singer.findAll";
    private static final String FIRST_NAMES = "select first_name from SINGER";

    @Override
    public Stream<Singer> findByCriteriaQuery(String firstName, String lastName) {
        logger.info("finding singer for first name and last name using Criteria query");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Singer> criteriaQuery = cb.createQuery(Singer.class); // a query that returns singer objects 
        Root<Singer> singerRoot = criteriaQuery.from(Singer.class); 
        singerRoot.fetch(Singer_.albums, JoinType.LEFT); // also join albums and instruments
        singerRoot.fetch(Singer_.instruments, JoinType.LEFT);
        criteriaQuery.select(singerRoot).distinct(true);
        Predicate criteria = cb.conjunction(); // starting with a neutral where class
        //if firstname is provided, add it to where clause
        if(StringUtils.isNotBlank(firstName)) {
            Predicate firstNamePredicate = cb.equal(singerRoot.get(Singer_.firstName), firstName);
            criteria = cb.and(criteria, firstNamePredicate);
        }
        if(StringUtils.isNotBlank(lastName)) {
            Predicate lastNamePredicate = cb.equal(singerRoot.get(Singer_.lastName), lastName);
            criteria = cb.and(criteria, lastNamePredicate);
        }
        criteriaQuery.where(criteria);
        return em.createQuery(criteriaQuery).getResultList().stream();
    }

    @Override
    public String findFirstNameById(Long id) {
        return em.createNamedQuery("Singer.getFirstNameById(?)").setParameter("id", 1L).getSingleResult().toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Stream<Singer> findAllByNativeQuery() {
        return em.createNativeQuery(ALL_SINGER_NATIVE_QUERY, "singerResult").getResultList().stream();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> findFirstNames() {
        var result = em.createNativeQuery(FIRST_NAMES, String.class).getResultStream().toList();
        return result;
    }

    @Override
    public Stream<Singer> findAll() {
        return em.createNamedQuery(findAll, Singer.class).getResultList().stream();
    }

    @Override
    public Stream<Singer> findAllWithAlbum() {
        return em.createNamedQuery(Singer.FIND_ALL_WITH_ALBUM, Singer.class).getResultList().stream();
    }

    @Override
    public Optional<Singer> findById(Long id) {
        TypedQuery<Singer> query = em.createNamedQuery("Singer.findById", Singer.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException nore) {
            return Optional.empty();
        }

    }

    @Override
    @Transactional
    // for inserting and updating.
    public void save(Singer singer) {
        if (singer.getId() == null) {
            em.persist(singer);
        } else {
            em.merge(singer);
        }
    }

    @Override
    @Transactional
    public void delete(Singer singer) {
        var mergedSinger = em.merge(singer); // because to use remove the object must be in a managed state and this
        em.remove(mergedSinger);
        logger.info("deleted singer with id : {}", singer.getId());
    }

}
