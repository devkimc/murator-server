package com.vvs.murator.video.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Video {

    @Id
    @Column(name = "video_id", nullable = false, length = 20)
    private String id;
}
