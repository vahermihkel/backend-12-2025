package ee.mihkel.rendipood.controller;

import ee.mihkel.rendipood.dto.RentalFilmDTO;
import ee.mihkel.rendipood.entity.Film;
import ee.mihkel.rendipood.entity.Rental;
import ee.mihkel.rendipood.entity.RentalFilm;
import ee.mihkel.rendipood.repository.FilmRepository;
import ee.mihkel.rendipood.repository.RentalFilmRepository;
import ee.mihkel.rendipood.repository.RentalRepository;
import ee.mihkel.rendipood.service.RentalService;
import ee.mihkel.rendipood.util.FeeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RentalController {

    @Autowired
    RentalService rentalService;

    @PostMapping("start-rental")
    public double startRental(@RequestBody List<RentalFilmDTO> rentalFilms) {

        return rentalService.startRental(rentalFilms);
    }

    @PostMapping("end-rental")
    public double endRental(@RequestBody List<RentalFilmDTO> rentalFilmDTO){
        return rentalService.endRental(rentalFilmDTO);
    }


}
