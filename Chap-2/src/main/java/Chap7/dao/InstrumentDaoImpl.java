package Chap7.dao;

import Chap7.entities.Instrument;
import jakarta.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class InstrumentDaoImpl implements InstrumentDao {
    private SessionFactory sessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(InstrumentDaoImpl.class);

    public InstrumentDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public Instrument save(Instrument instrument) {
        instrument = sessionFactory.getCurrentSession().merge(instrument); 
        return instrument;    
    }
}
