package ee.mihkel.rendipood.service;

import ee.mihkel.rendipood.dto.RentalFilmDTO;
import ee.mihkel.rendipood.entity.Film;
import ee.mihkel.rendipood.entity.Rental;
import ee.mihkel.rendipood.entity.RentalFilm;
import ee.mihkel.rendipood.repository.FilmRepository;
import ee.mihkel.rendipood.repository.RentalFilmRepository;
import ee.mihkel.rendipood.repository.RentalRepository;
import ee.mihkel.rendipood.util.FeeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private RentalFilmRepository rentalFilmRepository;

    public double startRental(List<RentalFilmDTO> rentalFilms) {
        double sum = 0;
        List<Film> films = new ArrayList<>();
        List<RentalFilm> rentalFilmList = new ArrayList<>();
        Rental rental = new Rental();

        for (RentalFilmDTO rentalFilmDTO : rentalFilms) {
            Film film = filmRepository.findById(rentalFilmDTO.getFilmId()).orElseThrow();
            if (!film.getInStock()) {
                throw new RuntimeException("Film is not in stock");
            }
            film.setInStock(false);
            films.add(film);
//            filmRepository.save(film); ---> kui List<Film> ei teeks ja korraga ei salvestaks

            // ehitan valmis RentalFilm entity mudelit.
            sum += FeeCalculator.initialFee(film.getType(), rentalFilmDTO.getDays());
            RentalFilm rentalFilm = getRentalFilm(rentalFilmDTO, film, rental);
            rentalFilmList.add(rentalFilm);
        }
        filmRepository.saveAll(films);
        saveRental(rental, sum, rentalFilmList);
        return sum;
    }

    private RentalFilm getRentalFilm(RentalFilmDTO rentalFilmDTO, Film film, Rental rental) {
        RentalFilm rentalFilm = new RentalFilm();
        rentalFilm.setFilm(film);
        rentalFilm.setInitialDays(rentalFilmDTO.getDays());
        rentalFilm.setLateDays(0);
        rentalFilm.setRental(rental);
        return rentalFilm;
    }

    private void saveRental(Rental rental, double sum, List<RentalFilm> rentalFilmList) {
        rental.setInitialFee(sum);
        rental.setLateFee(0);
        rental.setRentalFilms(rentalFilmList); // RentalFilm entity .save abil andmebaasi
        // tänu CascadeType.ALL reale
        rentalRepository.save(rental);
    }

    public double endRental(List<RentalFilmDTO> rentalFilmDTO) {
        double sum = 0;

        List<Film> films = new ArrayList<>();
        List<RentalFilm> rentalFilmsList = new ArrayList<>();
        List<Rental> rentals = new ArrayList<>();

        for (RentalFilmDTO dto: rentalFilmDTO){
            Film film = filmRepository.findById(dto.getFilmId()).orElseThrow();
            film.setInStock(true);
            films.add(film);

            RentalFilm rentalFilm = getRentalFilm(dto, rentalFilmsList);

            Rental rental = rentalRepository.findById(rentalFilm.getRental().getId()).orElseThrow();
            sum += rental.getLateFee() + FeeCalculator.lateFee(film.getType(), rentalFilm.getLateDays());
            rental.setLateFee(sum);
            rentals.add(rental);
        }

        filmRepository.saveAll(films);
        rentalRepository.saveAll(rentals);
        rentalFilmRepository.saveAll(rentalFilmsList);
        return sum;
    }

    private RentalFilm getRentalFilm(RentalFilmDTO dto, List<RentalFilm> rentalFilmsList) {
        RentalFilm rentalFilm = rentalFilmRepository.findByFilm_IdAndReturnedFalse(dto.getFilmId());
        int lateDays = Math.max(0, dto.getDays() - rentalFilm.getInitialDays());
        rentalFilm.setLateDays(lateDays);
        rentalFilm.setReturned(true);
        rentalFilmsList.add(rentalFilm);
        return rentalFilm;
    }
}
