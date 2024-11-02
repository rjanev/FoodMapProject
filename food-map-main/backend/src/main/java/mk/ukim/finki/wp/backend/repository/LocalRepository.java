package mk.ukim.finki.wp.backend.repository;

//import mk.ukim.finki.wp.proekt_veb.model.Local;


import jakarta.persistence.Table;
import mk.ukim.finki.wp.backend.model.Local;
import mk.ukim.finki.wp.backend.model.enumeration.LocalType;
import org.springframework.data.geo.Distance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public interface LocalRepository extends JpaRepository<Local, Long> {

    List<Local> findLocalsByType (LocalType type);

    List<Local> findLocalsByTypeAndDistanceLessThanEqual(LocalType type, Double distance);



    List<Local> findLocalsByDistanceLessThanEqual(Double distance);
    List<Local> findLocalsByType(Type type);

    Local findLocalByNameLike (String name);



}
