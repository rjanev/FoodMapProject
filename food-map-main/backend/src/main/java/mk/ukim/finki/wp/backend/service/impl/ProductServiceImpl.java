package mk.ukim.finki.wp.backend.service.impl;

//import mk.ukim.finki.wp.proekt_veb.model.Local;


import mk.ukim.finki.wp.backend.model.Local;
import mk.ukim.finki.wp.backend.model.Product;
import mk.ukim.finki.wp.backend.model.enumeration.ProductType;
import mk.ukim.finki.wp.backend.repository.ProductRepository;
import mk.ukim.finki.wp.backend.service.LocalService;
import mk.ukim.finki.wp.backend.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final LocalService localService;

    public ProductServiceImpl(ProductRepository productRepository, LocalService localService) {
        this.productRepository = productRepository;
        this.localService = localService;
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public List<Product> findByLocal(Long localId) {
        Local local=localService.findById(localId).get();
        return this.productRepository.findProductsByLocal(local);
    }


    @Override
    public Optional<Product> save(String name, Double price, String description, ProductType type, Long localId) {
        Local local = this.localService.findById(localId).get();
        if(description == null) description = "";
        Product product = new Product(name, price,description,type,local);
        return Optional.of(this.productRepository.save(product));
    }

    @Override
    public Optional<Product> edit(Long id, String name, Double price, String description, ProductType type, Long localId) {
        Product product=findById(id).get();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setType(type);
        Local local = this.localService.findById(localId).get();
        product.setLocal(local);
        return Optional.of(this.productRepository.save(product));
    }

    @Override
    public void deleteById(Long id) {
        Product product=findById(id).get();
        productRepository.delete(product);
    }

        @Override
        public List<Product> findByType(ProductType type) {
        return this.productRepository.findProductsByType(type);
    }
}
