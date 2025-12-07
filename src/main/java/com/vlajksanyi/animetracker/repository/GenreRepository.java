package com.vlajksanyi.animetracker.repository;

import com.vlajksanyi.animetracker.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
