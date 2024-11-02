package mk.ukim.finki.wp.backend.repository;

//import mk.ukim.finki.wp.proekt_veb.model.Local;


import mk.ukim.finki.wp.backend.model.Local;
import mk.ukim.finki.wp.backend.model.Product;
import mk.ukim.finki.wp.backend.model.enumeration.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findProductsByLocal(Local local);

    public List<Product> findProductsByType(ProductType type);


}
