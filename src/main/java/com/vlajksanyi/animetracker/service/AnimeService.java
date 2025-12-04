package com.vlajksanyi.animetracker.service;

import com.vlajksanyi.animetracker.model.Anime;
import com.vlajksanyi.animetracker.model.Genre;
import com.vlajksanyi.animetracker.repository.AnimeRepository;
import com.vlajksanyi.animetracker.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeService {

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private GenreRepository genreRepository;

    public List<Anime> getAllAnimes() {
        return animeRepository.findAll();
    }

    public Anime getAnimeById(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anime not found"));
    }

    public Anime addAnime(Anime anime, Long genreId) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException("Genre not found with ID: " + genreId));
        anime.setGenre(genre);
        return animeRepository.save(anime);
    }

    public Anime updateAnime(Long id, Anime animeDetails) {
        Anime anime = getAnimeById(id);

        anime.setTitle(animeDetails.getTitle());
        anime.setEpisodeCount(animeDetails.getEpisodeCount());
        anime.setRating(animeDetails.getRating());
        anime.setStatus(animeDetails.getStatus());

        return animeRepository.save(anime);
    }

    public void deleteAnime(Long id) {
        animeRepository.deleteById(id);
    }

}
