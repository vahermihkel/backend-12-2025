package ee.mihkel.rendipood.repository;

import ee.mihkel.rendipood.entity.RentalFilm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalFilmRepository extends JpaRepository<RentalFilm,Long> {

    RentalFilm findByFilm_IdAndReturnedFalse(Long id);
}
