package Chap10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Chap10.entities.SingerAudit;

public interface SingerAuditRepository extends JpaRepository<SingerAudit, Long> {

}
