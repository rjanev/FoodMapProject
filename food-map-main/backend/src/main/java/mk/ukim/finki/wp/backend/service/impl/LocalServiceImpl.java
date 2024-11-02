package mk.ukim.finki.wp.backend.service.impl;

//import mk.ukim.finki.wp.proekt_veb.model.Local;

import mk.ukim.finki.wp.backend.model.Local;
import mk.ukim.finki.wp.backend.model.enumeration.LocalType;
import mk.ukim.finki.wp.backend.repository.LocalRepository;
import mk.ukim.finki.wp.backend.service.LocalService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class LocalServiceImpl implements LocalService {

    private final LocalRepository localRepository;

    public LocalServiceImpl(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    @Override
    public List<Local> findAll() {
        return this.localRepository.findAll();
    }

    @Override
    public List<Local> findByType(LocalType type) {
        if(type.equals(LocalType.ALL))
            return this.findAll();
        return localRepository.findLocalsByType(type);
    }


    @Override
    public Optional<Local> findById(Long id) {
        return this.localRepository.findById(id);
    }

    @Override
    public Optional<Local> findByName(String name) {

     return Optional.ofNullable(localRepository.findLocalByNameLike(name));
    }

    @Override
    public Optional<Local> save(String name, String address, LocalType type, String workingHours, Double distance, String image,String link) {
        Local local = new Local(name, address, type, workingHours, distance, image,link);
        return Optional.of(this.localRepository.save(local));
    }

    @Override
    public Optional<Local> edit(Long id, String name, String address, LocalType type, String workingHours, Double distance, String image,String link) {
        Local local=findById(id).get();
        local.setName(name);
        local.setAddress(address);
        local.setType(type);
        local.setWorkingHours(workingHours);
        local.setDistance(distance);
        local.setImage(image);
        local.setImage(link);
        return Optional.of(this.localRepository.save(local));
    }


    @Override
    public List<Local> filter(Double distance, LocalType type) {
        if(type!=null && distance!=null)
            return localRepository.findLocalsByTypeAndDistanceLessThanEqual(type, distance);
        else if (distance!=null){
            return localRepository.findLocalsByDistanceLessThanEqual(distance);
        }
        else if(type!=null)
        {
            return localRepository.findLocalsByType(type);
        }
        else  return localRepository.findAll();
    }

    @Override
    public List<Local> sorted() {
        List<Local> locals=localRepository.findAll();
        List<Local> sortedLocals = locals.stream()
                .sorted((local1, local2) -> Integer.compare( local2.getLikee()-local2.getDislike(), local1.getLikee()-local1.getDislike()))
                .collect(Collectors.toList());
        return sortedLocals;
    }

    @Override
    public void deleteById(Long id) {
        Local local=findById(id).get();
        localRepository.delete(local);
    }

    @Override
    public Local like(Long id) {
        Local local=findById(id).get();
        local.setLikee(local.getLikee()+1);
        return localRepository.save(local);
    }

    @Override
    public Local dislike(Long id) {
        Local local=findById(id).get();
        local.setLikee(local.getLikee()-1);
        if (local.getLikee() <= 0){
            local.setLikee(0);
        }
       return localRepository.save(local);
    }

}
