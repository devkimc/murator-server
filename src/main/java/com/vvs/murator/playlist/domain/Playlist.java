package com.vvs.murator.playlist.domain;

import com.vvs.murator.music.domain.Music;
import com.vvs.murator.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private Long id;

    @Column(nullable = false, length = 30)
    @NotBlank
    private String title;

    @Column(nullable = false, length = 200)
    @NotBlank
    private String description;

    @Column(nullable = false)
    @NotBlank
    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User writer;

    @Builder.Default
    @OneToMany(mappedBy = "playlist")
    private List<Music> musics = new ArrayList<>();


}
