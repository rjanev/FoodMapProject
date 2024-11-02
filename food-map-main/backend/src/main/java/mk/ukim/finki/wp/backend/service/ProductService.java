package mk.ukim.finki.wp.backend.service;

//import mk.ukim.finki.wp.proekt_veb.model.Local;


import mk.ukim.finki.wp.backend.model.Product;
import mk.ukim.finki.wp.backend.model.enumeration.ProductType;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();
    Optional<Product> findById(Long id);

    List<Product> findByLocal(Long localId);
    Optional<Product> save(String name, Double price, String description, ProductType type, Long localId);
    Optional<Product> edit(Long id, String name, Double price, String description, ProductType type, Long localId);
    void deleteById(Long id);

     List<Product> findByType(ProductType type);

}
