package Chap9.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Chap7.entities.Album;
import Chap7.entities.Singer;
import Chap9.exception.TitleTooLongException;
import Chap9.repos.AlbumRepo;
import Chap9.repos.SingerRepo;

@Service
public class AllServiceImpl implements AllService {

    private static final Logger logger = LoggerFactory.getLogger(AllServiceImpl.class);

    @Autowired
    private SingerRepo singerRepo;
    @Autowired
    private AlbumRepo albumRepo;

   

    @Override
    @Transactional(readOnly = true)
    public Optional<Singer> findByIdWithAlbums(Long id) {
        Optional<Singer> singer = singerRepo.findById(id);
        singer.ifPresent(s -> s.setAlbums(albumRepo.findBySinger(s).collect(Collectors.toSet())));
        return singer;
    }

    @Override
    public Stream<Singer> findAllWithAlbums() {
        var singers = singerRepo.findAll().toList();
        logger.info("singer.size() -> {}", singers.size());
        singers.forEach(s -> s.setAlbums(albumRepo.findBySinger(s).collect(Collectors.toSet())));
        return singers.stream();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(Singer singer) {
        singerRepo.save(singer);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Long countSingers() {
        return singerRepo.countAllSingers();
    }

    @Override
    @Transactional(rollbackFor = TitleTooLongException.class)
    public void saveSingerWithAlbums(Singer s, Set<Album> albums) throws TitleTooLongException {
        var singer = singerRepo.save(s);
        if(singer != null){
            albums.forEach(a -> a.setSinger(singer));
            albumRepo.save(albums);
        } 

        
    }
}
