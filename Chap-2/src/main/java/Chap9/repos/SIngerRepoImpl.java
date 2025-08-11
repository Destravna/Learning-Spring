package Chap9.repos;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import Chap7.entities.Singer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class SIngerRepoImpl implements SingerRepo{
    
    @PersistenceContext private EntityManager em;
    
    @Override
    public Stream<Singer> findAll() {
        return em.createNamedQuery("Singer.findAll", Singer.class).getResultList().stream();
    }

    @Override
    public Optional<Singer> findById(Long id) {
        return Optional.ofNullable(em.createNamedQuery("Singer.findById", Singer.class).setParameter("id", id).getSingleResult());
    }

    @Override
    public Singer save(Singer singer) {
        if(singer.getId() != null){
            singer = em.merge(singer);
            return singer;
        }
        else {
            em.persist(singer);
            return singer;
        }
    }

    @Override
    public Long countAllSingers() {
        return findAll().count();
    }
}
