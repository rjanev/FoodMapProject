package mk.ukim.finki.wp.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.wp.backend.model.enumeration.ProductType;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private String description; //optional

    @Enumerated(value = EnumType.STRING)
    private ProductType type;

    @ManyToOne
    private Local local;

    public Product(String name, Double price, String description, ProductType type, Local local) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
        this.local = local;
    }
}
