package com.vlajksanyi.animetracker.repository;

import com.vlajksanyi.animetracker.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {
    List<Anime> findByGenreId(Long genreId);
}
