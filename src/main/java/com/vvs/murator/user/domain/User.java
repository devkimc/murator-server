package com.vvs.murator.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    @NotBlank
    private String email;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(nullable = false)
    @NotBlank
    private String oauthId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OauthType oauthType;
}
