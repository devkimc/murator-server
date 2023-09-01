package com.vvs.murator.playlist.sevice;

import com.vvs.murator.feign.youtube.YoutubeFeignClient;
import com.vvs.murator.feign.youtube.YoutubeSearchListRes;
import com.vvs.murator.playlist.dto.AddPlaylistReq;
import com.vvs.murator.playlist.dto.MusicInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayServiceImpl implements PlaylistService {

    private final YoutubeFeignClient youtubeFeignClient;

    @Value("${app.youtube.api-key}")
    private String apiKey;

    @Override
    @Transactional
    public Boolean addPlayList(AddPlaylistReq addPlaylistReq) {

        List<YoutubeSearchListRes> searchList = new ArrayList<>();

        // 1. 유튜브 api 로 노래 검색
        for (MusicInfo musicInfo: addPlaylistReq.getMusicInfoList()
        ) {
            StringBuilder keyword = new StringBuilder();

            keyword.append(musicInfo.getName());
            keyword.append(" ");
            keyword.append(musicInfo.getArtist());

            // TODO: Music 테이블에 같은 노래가 있는지 체크 후, 있다면 같은 VideoId 사용
            YoutubeSearchListRes youtubeSearchListRes = youtubeFeignClient.getSearchList(
                            "snippet",
                            1,
                            apiKey,
                            keyword.toString(),
                            "video",
                            "true",
                            "true")
                    .getBody();

            // 2. Music 테이블에 같은 노래가 존재하는지 검색

            // 2.1 존재할 경우 Music 테이블에 저장하지 않음, Video 도 이미 존재하므로 저장하지 않음

            // 2.2 존재하지 않을 경우 Music 테이블에 저장

            // 3. Video 테이블에 저장


            searchList.add(youtubeSearchListRes);
        }


        System.out.println("searchList.getBody().getItems() = " + searchList
                .getBody()
                .getItems()
                .get(0)
                .getSnippet()
                .getTitle()
        );

        return null;
    }
}
