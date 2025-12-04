package com.vlajksanyi.animetracker.controller;

import com.vlajksanyi.animetracker.model.Anime;
import com.vlajksanyi.animetracker.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animes")
public class AnimeController {

    @Autowired
    private AnimeService animeService;

    @GetMapping
    public List<Anime> getAllAnimes() {
        return animeService.getAllAnimes();
    }

    @GetMapping("/{id}")
    public Anime getAnimeById(@PathVariable Long id) {
        return animeService.getAnimeById(id);
    }

    @PostMapping("/genre/{genreId}")
    public Anime addAnime(@RequestBody Anime anime, @PathVariable Long genreId) {
        return animeService.addAnime(anime, genreId);
    }

    @PutMapping("/{id}")
    public Anime updateAnime(@PathVariable Long id, @RequestBody Anime animeDetails) {
        return animeService.updateAnime(id, animeDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        animeService.deleteAnime(id);
        return ResponseEntity.ok().build();
    }

}
