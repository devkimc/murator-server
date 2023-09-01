package com.vvs.murator.playlist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class AddPlaylistReq {

    @Size(max = 100)
    @NotBlank
    private String title;

    @Size(max = 200)
    @NotBlank
    private String description;

    @Size(min = 1, max = 10)
    @NotNull
    List<MusicInfo> musicInfoList = new ArrayList<>();
}
