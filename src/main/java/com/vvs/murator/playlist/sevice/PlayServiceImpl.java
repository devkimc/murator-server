package com.vvs.murator.playlist.sevice;

import com.vvs.murator.feign.youtube.YoutubeFeignClient;
import com.vvs.murator.feign.youtube.YoutubeSearchListRes;
import com.vvs.murator.music.domain.Music;
import com.vvs.murator.music.repository.MusicRepository;
import com.vvs.murator.playlist.domain.Playlist;
import com.vvs.murator.playlist.dto.AddPlaylistReq;
import com.vvs.murator.playlist.dto.MusicInfo;
import com.vvs.murator.playlist.repository.PlaylistRepository;
import com.vvs.murator.user.domain.User;
import com.vvs.murator.video.domain.Video;
import com.vvs.murator.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayServiceImpl implements PlaylistService {

    private final YoutubeFeignClient youtubeFeignClient;

    private final PlaylistRepository playlistRepository;
    private final MusicRepository musicRepository;
    private final VideoRepository videoRepository;

    @Value("${app.youtube.api-key}")
    private String apiKey;

    @Override
    @Transactional
    public Boolean addPlayList(AddPlaylistReq addPlaylistReq, User user) {

        // 1. 유튜브 api 로 노래 검색
        for (MusicInfo musicInfo : addPlaylistReq.getMusicInfoList()
        ) {
            StringBuilder keyword = new StringBuilder();
            String musicName = musicInfo.getName();
            String artist = musicInfo.getArtist();

            keyword.append(musicName);
            keyword.append(" ");
            keyword.append(artist);
            YoutubeSearchListRes.Item item = youtubeFeignClient.getSearchList(
                            "snippet",
                            1,
                            apiKey,
                            keyword.toString(),
                            "video",
                            "true",
                            "true")
                    .getBody()
                    .getItems()
                    .get(0);

            Playlist playlist = Playlist.builder()
                    .title(addPlaylistReq.getTitle())
                    .description(addPlaylistReq.getDescription())
                    .thumbnail(item.getSnippet().getThumbnails().getHigh().getUrl())
                    .writer(user)
                    .build();

            // 2. Music 테이블에 같은 노래가 존재하는지 검색
            Optional<Music> findMusic = musicRepository.findByNameAndArtist(musicName, artist);

            // 2.1 존재할 경우 Music 테이블에 저장하지 않음, Video 도 이미 존재하므로 저장하지 않음
            if (findMusic.isPresent()) {
                Music music = findMusic.get();
                playlist.getMusics().add(music);

                playlistRepository.save(playlist);
                continue;
            }

            playlistRepository.save(playlist);

            // 3. Video 테이블에 저장
            Video video = Video.builder()
                    .id(item.getId().getVideoId())
                    .build();
            videoRepository.save(video);

            // 2.2 존재하지 않을 경우 Music 테이블에 저장
            Music music = Music.builder()
                    .name(musicName)
                    .artist(artist)
                    .playlist(playlist)
                    .video(video)
                    .build();
            musicRepository.save(music);
        }

        return true;
    }
}
