package com.vlajksanyi.animetracker;

import com.vlajksanyi.animetracker.model.Genre;
import com.vlajksanyi.animetracker.repository.GenreRepository;
import com.vlajksanyi.animetracker.service.GenreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    @Test
    void getAllGenres_shouldReturnAllGenres() {
        Genre g1 = new Genre(1L, "Shonen", "Often associated with action, adventure, and a focus on themes like friendship, perseverance, and overcoming challenges through a male protagonist", null);
        Genre g2 = new Genre(2L, "Shojo", "Romance, fantasy, and slice-of-life elements", null);
        when(genreRepository.findAll()).thenReturn(Arrays.asList(g1, g2));

        List<Genre> result = genreService.getAllGenres();

        assertEquals(2, result.size());
        assertEquals("Shonen", result.get(0).getName());

        verify(genreRepository, times(1)).findAll();
    }

    @Test
    void addGenre_shouldReturnSavedGenre() {
        Genre genre = new Genre(null, "Mecha", "Robots", null);
        Genre savedGenre = new Genre(1L, "Mecha", "Robots", null);

        when(genreRepository.save(any(Genre.class))).thenReturn(savedGenre);

        Genre result = genreService.addGenre(genre);

        assertEquals(1L, result.getId());
        assertEquals("Mecha", result.getName());
    }

}
