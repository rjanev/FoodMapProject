package mk.ukim.finki.wp.backend.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.wp.backend.model.enumeration.LocalType;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Local {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    private String name;
    private String address;
    private String workingHours;
    private Double distance;

    @Enumerated(value = EnumType.STRING)
    private LocalType type;

    private Integer dislike;
    private Integer likee;
    private String image;

    private String link;


    public Local(String name, String address, LocalType type, String workingHours, Double distance,String image,String link) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.workingHours=workingHours;
        this.distance=distance;
        likee=0;
        dislike=0;
        this.image=image;
        this.link = link;
    }
}
