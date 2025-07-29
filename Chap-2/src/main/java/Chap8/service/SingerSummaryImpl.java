package Chap8.service;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import Chap8.views.SingerSummary;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Repository
public class SingerSummaryImpl implements SingerSummaryService {
    @PersistenceContext EntityManager em;
    
    @Override
    @SuppressWarnings({"unchecked"})
    public Stream<SingerSummary> findAllAsRecord() {
        return em.createQuery(SingerSummaryService.ALL_SINGER_SUMMARY_RECORD_JPQL_QUERY).getResultList().stream().map(
            obj -> {
                Object[] values = (Object []) obj;
                return new SingerSummary((String) values[0], (String) values[1], (String)values[2]);
            }
        );
    }
}
