package com.vvs.murator.playlist.sevice;

import com.vvs.murator.playlist.dto.AddPlaylistReq;
import com.vvs.murator.user.domain.User;

public interface PlaylistService {

    Boolean addPlayList(AddPlaylistReq addPlaylistReq, User user);
}
