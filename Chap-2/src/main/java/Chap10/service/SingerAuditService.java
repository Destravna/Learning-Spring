package Chap10.service;

import java.util.stream.Stream;

import Chap10.entities.SingerAudit;

public interface SingerAuditService {

    Stream<SingerAudit> findAll();
    SingerAudit findById(Long id);
    SingerAudit save(SingerAudit singer);

}
