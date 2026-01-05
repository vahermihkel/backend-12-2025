package ee.mihkel.rendipood.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RentalFilm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Film film;
    private int initialDays;
    private int lateDays;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "rental_id")
    private Rental rental;

    // @Value("false")
    private boolean returned;
}
