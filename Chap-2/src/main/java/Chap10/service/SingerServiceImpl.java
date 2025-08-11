package Chap10.service;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import Chap10.repository.SingerRepository;
import Chap10.repository.SingerRepository.FullName;
import Chap7.entities.Singer;

@Service
public class SingerServiceImpl implements SingerService {

    private final SingerRepository singerRepository;

    public SingerServiceImpl(SingerRepository singerRepository) {
        this.singerRepository = singerRepository;
    }

    @Override
    public Stream<Singer> findAll() {
        return StreamSupport.stream(singerRepository.findAll().spliterator(), false);
    }

    @Override
    public Stream<Singer> findByFirstName(String firstName) {
        return StreamSupport.stream(singerRepository.findByFirstName(firstName).spliterator(), false);
    }

    @Override
    public Stream<Singer> findByFirstNameAndLastName(String firstName, String lastName) {
        return StreamSupport.stream(singerRepository.findByFirstNameAndLastName(firstName,lastName).spliterator(), false);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, label = "modifying")
    public Singer updateFirstName(String firstName, Long id) {
        singerRepository.findById(id).ifPresent(s -> singerRepository.setFirstNameFor(firstName, id));
        var singer  = singerRepository.findById(id).orElse(null);
        return singer;
    }

    @Override
    public Object[] getFullNameById(Long id) {
        var fullName = singerRepository.getFullNameById(id);
        return fullName;    
    }

    @Override
    public Optional<FullName> getFullNameByIdInt(Long id) {
        return singerRepository.getFullNameByIdInt(id);
    }

}
