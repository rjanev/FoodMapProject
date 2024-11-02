package mk.ukim.finki.wp.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private LocalDate date;

    @ManyToOne
    private Local local;

    public Comment(String comment, Local local) {
        this.comment = comment;
        this.local = local;
        date= LocalDate.now();
    }
}
