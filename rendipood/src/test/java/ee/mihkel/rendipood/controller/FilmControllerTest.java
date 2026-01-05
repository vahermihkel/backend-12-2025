package ee.mihkel.rendipood.controller;

import ee.mihkel.rendipood.entity.Film;
import ee.mihkel.rendipood.repository.FilmRepository;
import ee.mihkel.rendipood.service.FilmService;
import io.swagger.v3.core.util.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(FilmController.class)
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FilmService filmService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void addFilm() throws Exception {
        Film film = new Film();
        mockMvc.perform(post("/films").content(Json.pretty(film)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void addMultipleFilms() throws Exception {
        Film film = new Film();
        mockMvc.perform(post("/films-multi").content(Json.pretty(List.of(film))).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteFilm() throws Exception {
        mockMvc.perform(delete("/films/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateFilm() throws Exception {
        mockMvc.perform(patch("/films-type?id=2&type=OLD"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void filmsInStore() throws Exception {
        mockMvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}