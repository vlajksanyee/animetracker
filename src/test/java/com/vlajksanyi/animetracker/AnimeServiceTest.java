package com.vlajksanyi.animetracker;

import com.vlajksanyi.animetracker.model.Anime;
import com.vlajksanyi.animetracker.model.Genre;
import com.vlajksanyi.animetracker.repository.AnimeRepository;
import com.vlajksanyi.animetracker.repository.GenreRepository;
import com.vlajksanyi.animetracker.service.AnimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimeServiceTest {

    @Mock
    private AnimeRepository animeRepository;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private AnimeService animeService;

    @Test
    void getAllAnimes_shouldReturnList() {
        Anime a1 = new Anime(1L, "Naruto", 500, 8.5, "COMPLETED", null);
        when(animeRepository.findAll()).thenReturn(List.of(a1));

        List<Anime> result = animeService.getAllAnimes();

        assertEquals(1, result.size());
        assertEquals("Naruto", result.get(0).getTitle());
    }

    @Test
    void createAnime_shouldThrowErrorIfGenreNotFound() {
        Long genreId = 99L;
        Anime animeInput = new Anime();
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            animeService.addAnime(animeInput, genreId);
        });
    }

    @Test
    void getAnimeById_shouldReturnAnime() {
        Anime anime = new Anime(1L, "Naruto", 500, 8.5, "COMPLETED", null);
        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));

        Anime result = animeService.getAnimeById(1L);

        assertEquals("Naruto", result.getTitle());
    }

    @Test
    void getAnimeById_shouldThrowErrorIfNotFound() {
        when(animeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> animeService.getAnimeById(99L));
    }

    @Test
    void createAnime_shouldSaveAnimeWithGenre() {
        Long genreId = 1L;
        Genre genre = new Genre(genreId, "Shonen", "Action", null);
        Anime animeInput = new Anime(null, "Bleach", 366, 8.0, "COMPLETED", null);
        Anime animeSaved = new Anime(1L, "Bleach", 366, 8.0, "COMPLETED", genre);

        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        when(animeRepository.save(any(Anime.class))).thenReturn(animeSaved);

        Anime result = animeService.addAnime(animeInput, genreId);

        assertNotNull(result.getId());
        assertEquals("Bleach", result.getTitle());
        assertEquals("Shonen", result.getGenre().getName());
    }

    @Test
    void updateAnime_shouldUpdateFields() {
        Anime existingAnime = new Anime(1L, "Old Title", 10, 5.0, "WATCHING", null);
        Anime updateDetails = new Anime(null, "New Title", 20, 9.0, "COMPLETED", null);

        when(animeRepository.findById(1L)).thenReturn(Optional.of(existingAnime));
        when(animeRepository.save(existingAnime)).thenReturn(existingAnime);

        Anime result = animeService.updateAnime(1L, updateDetails);

        assertEquals("New Title", result.getTitle());
        assertEquals("COMPLETED", result.getStatus());
        assertEquals(20, result.getEpisodeCount());
    }

    @Test
    void deleteAnime_shouldCallRepository() {
        animeService.deleteAnime(1L);

        verify(animeRepository, times(1)).deleteById(1L);
    }
}
