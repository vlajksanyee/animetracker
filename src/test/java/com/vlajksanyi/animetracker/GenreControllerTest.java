package com.vlajksanyi.animetracker;

import com.vlajksanyi.animetracker.controller.GenreController;
import com.vlajksanyi.animetracker.model.Genre;
import com.vlajksanyi.animetracker.service.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GenreService genreService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllGenres_shouldReturnJsonList() throws Exception {
        Genre g1 = new Genre(1L, "Horror", "Scary", null);
        when(genreService.getAllGenres()).thenReturn(List.of(g1));

        mockMvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Horror"));
    }

    @Test
    void createGenre_shouldReturnCreatedGenre() throws Exception {
        Genre genreToSave = new Genre(null, "Comedy", "Funny", null);
        Genre savedGenre = new Genre(1L, "Comedy", "Funny", null);

        when(genreService.addGenre(any(Genre.class))).thenReturn(savedGenre);

        mockMvc.perform(post("/api/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(genreToSave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Comedy"));
    }

}
