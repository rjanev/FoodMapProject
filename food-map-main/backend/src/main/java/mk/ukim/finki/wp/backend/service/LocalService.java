package mk.ukim.finki.wp.backend.service;

//import mk.ukim.finki.wp.proekt_veb.model.Local;



import jakarta.persistence.criteria.CriteriaBuilder;
import mk.ukim.finki.wp.backend.model.Local;
import mk.ukim.finki.wp.backend.model.enumeration.LocalType;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public interface LocalService {

    List<Local> findAll();

    List<Local> findByType(LocalType type);
    Optional<Local> findById(Long id);
    Optional<Local> findByName(String name);
    Optional<Local> save(String name, String address, LocalType type, String workingHours, Double distance,String image,String link);
    Optional<Local> edit(Long id, String name, String address, LocalType type, String workingHours, Double distance,String image ,String link);

    List<Local> filter(Double distance, LocalType type);

    List<Local> sorted ();
    void deleteById(Long id);

    Local like(Long id);
    Local dislike(Long id);




}
