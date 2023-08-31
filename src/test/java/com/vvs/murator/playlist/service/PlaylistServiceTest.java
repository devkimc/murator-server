package com.vvs.murator.playlist.service;

import com.vvs.murator.playlist.sevice.PlaylistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PlaylistServiceTest {

    @Autowired
    PlaylistService playlistService;

    @Test
    public void aaa() throws Exception {
        playlistService.addPlayList("릴러말즈 끝나지 않은 얘기");
        //given

        //when

        //then
    }
}
