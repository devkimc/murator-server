package com.vvs.murator.music.repository;

import com.vvs.murator.music.domain.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
}
