package com.vvs.murator.music.repository;

import com.vvs.murator.music.domain.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MusicRepository extends JpaRepository<Music, Long> {

    Optional<Music> findByNameAndArtist(String name, String artist);
}
