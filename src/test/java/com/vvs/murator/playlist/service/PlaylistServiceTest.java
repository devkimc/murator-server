package com.vvs.murator.playlist.service;

import com.vvs.murator.common.fixture.UserFixture;
import com.vvs.murator.music.domain.Music;
import com.vvs.murator.music.repository.MusicRepository;
import com.vvs.murator.playlist.domain.Playlist;
import com.vvs.murator.playlist.dto.AddPlaylistReq;
import com.vvs.murator.playlist.dto.MusicInfo;
import com.vvs.murator.playlist.repository.PlaylistRepository;
import com.vvs.murator.playlist.sevice.PlaylistService;
import com.vvs.murator.user.domain.User;
import com.vvs.murator.user.dto.SocialJoinRequest;
import com.vvs.murator.user.service.UserService;
import com.vvs.murator.video.domain.Video;
import com.vvs.murator.video.repository.VideoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static com.vvs.murator.common.fixture.PlaylistFixture.*;
import static com.vvs.murator.common.fixture.UserFixture.*;

@SpringBootTest
public class PlaylistServiceTest {

    private User firstUser;

    @Autowired
    PlaylistService playlistService;

    @Autowired
    UserService userService;

    @Autowired
    PlaylistRepository playlistRepository;

    @Autowired
    MusicRepository musicRepository;

    @Autowired
    VideoRepository videoRepository;

    @BeforeEach
    void setUp() {
        SocialJoinRequest socialJoinRequest = SocialJoinRequest.builder()
                .socialUserId(socialUserId1)
                .email(email1)
                .nickname(nickname1)
                .socialType(socialType1)
                .build();

        firstUser = userService.join(socialJoinRequest);
    }

    @DisplayName("플레이리스트를 등록한다.")
    @Test
    public void create_success() throws Exception {

        //given
        MusicInfo musicInfo = MusicInfo.builder()
                .name(musicName)
                .artist(artist)
                .build();

        AddPlaylistReq addPlaylistReq = AddPlaylistReq.builder()
                .title(title)
                .description(description)
                .musicInfoList(Arrays.asList(musicInfo))
                .build();

        //when
        playlistService.addPlayList(addPlaylistReq, firstUser);

        Playlist playlist = playlistRepository.findAll().get(0);
        Music music = musicRepository.findAll().get(0);
        Video video = videoRepository.findAll().get(0);

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(title, playlist.getTitle()),
                () -> Assertions.assertEquals(artist, music.getArtist()),
                () -> Assertions.assertEquals("BAp0eCv18Gs", video.getId())
        );
    }
}
