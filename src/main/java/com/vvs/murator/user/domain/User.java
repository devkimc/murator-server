package com.vvs.murator.user.domain;

import com.vvs.murator.playlist.domain.Playlist;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 30)
    @NotBlank
    private String email;

    @Column(nullable = false, unique = true, length = 20)
    @NotBlank
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(nullable = false, length = 30)
    @NotBlank
    private String socialUserId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Builder.Default
    @OneToMany(mappedBy = "writer")
    private List<Playlist> playlists = new ArrayList<>();
}
