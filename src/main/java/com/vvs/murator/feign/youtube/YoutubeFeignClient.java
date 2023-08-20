package com.vvs.murator.feign.youtube;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "google", url = "https://www.googleapis.com/youtube")
public interface YoutubeFeignClient {

    @GetMapping(value = "/v3/search", consumes = "application/x-www-form-urlencoded;charset=utf-8")
    ResponseEntity<YoutubeSearchListResponse> getSearchList(
            @RequestParam("part") String part,
            @RequestParam("maxResults") int maxResults,
            @RequestParam("key") String apiKey,
            @RequestParam("type") String type,
            @RequestParam("videoEmbeddable") String videoEmbeddable,
            @RequestParam("videoSyndicated") String videoSyndicated,
            @RequestHeader("Authorization") String accessToken
    );
}
