package com.vvs.murator.media.domain;

import com.vvs.murator.playlist.domain.Playlist;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class Media {

    @Id
    @Column(name = "media_id", nullable = false, length = 50)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;
}
