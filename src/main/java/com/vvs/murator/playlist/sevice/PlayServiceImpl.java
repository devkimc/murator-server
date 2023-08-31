package com.vvs.murator.playlist.sevice;

import com.vvs.murator.feign.youtube.YoutubeFeignClient;
import com.vvs.murator.feign.youtube.YoutubeSearchListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayServiceImpl implements PlaylistService {

    private final YoutubeFeignClient youtubeFeignClient;

    @Value("${app.youtube.api-key}")
    private String apiKey;

    @Override
    @Transactional
    public Boolean addPlayList(String keyword) {

        ResponseEntity<YoutubeSearchListResponse> searchList = youtubeFeignClient.getSearchList(
                "snippet", 1, apiKey, keyword,"video", "true", "true"
        );

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
