package com.vlajksanyi.animetracker;

import com.vlajksanyi.animetracker.controller.AnimeController;
import com.vlajksanyi.animetracker.model.Anime;
import com.vlajksanyi.animetracker.service.AnimeService;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnimeController.class)
class AnimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AnimeService animeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllAnimes_shouldReturnList() throws Exception {
        Anime a1 = new Anime(1L, "One Piece", 1000, 9.0, "WATCHING", null);
        when(animeService.getAllAnimes()).thenReturn(List.of(a1));

        mockMvc.perform(get("/api/animes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("One Piece"));
    }

    @Test
    void getAnimeById_shouldReturnAnime() throws Exception {
        Anime anime = new Anime(1L, "Bleach", 366, 8.0, "COMPLETED", null);
        when(animeService.getAnimeById(1L)).thenReturn(anime);

        mockMvc.perform(get("/api/animes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Bleach"));
    }

    @Test
    void createAnime_shouldReturnCreatedAnime() throws Exception {
        Long genreId = 1L;
        Anime input = new Anime(null, "Death Note", 37, 9.5, "COMPLETED", null);
        Anime saved = new Anime(1L, "Death Note", 37, 9.5, "COMPLETED", null);

        when(animeService.addAnime(any(Anime.class), eq(genreId))).thenReturn(saved);

        mockMvc.perform(post("/api/animes/genre/{genreId}", genreId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Death Note"));
    }

    @Test
    void updateAnime_shouldReturnUpdatedAnime() throws Exception {
        Anime updateInfo = new Anime(null, "Updated Title", 100, 10.0, "COMPLETED", null);
        Anime updatedAnime = new Anime(1L, "Updated Title", 100, 10.0, "COMPLETED", null);

        when(animeService.updateAnime(eq(1L), any(Anime.class))).thenReturn(updatedAnime);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/animes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateInfo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void deleteAnime_shouldReturnOk() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/api/animes/{id}", 1L))
                .andExpect(status().isOk());

        verify(animeService).deleteAnime(1L);
    }
}
