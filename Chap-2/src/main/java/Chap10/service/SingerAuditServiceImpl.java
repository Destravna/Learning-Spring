package Chap10.service;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Chap10.entities.SingerAudit;
import Chap10.repository.SingerAuditRepository;

@Service
public class SingerAuditServiceImpl implements SingerAuditService {

    @Autowired SingerAuditRepository singerAuditRepository;

    @Override
    public Stream<SingerAudit> findAll() {
        return StreamSupport.stream(singerAuditRepository.findAll().spliterator(), false);
    }

    @Override
    public SingerAudit findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public SingerAudit save(SingerAudit singer) {
        SingerAudit singerAudit = singerAuditRepository.save(singer);
        return singerAudit;
    }
    
}
