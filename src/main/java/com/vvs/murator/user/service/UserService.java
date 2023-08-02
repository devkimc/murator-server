package com.vvs.murator.user.service;

import com.vvs.murator.user.domain.User;
import com.vvs.murator.user.dto.KakaoLoginResponse;
import com.vvs.murator.user.dto.SocialJoinRequest;

public interface UserService {
    KakaoLoginResponse kakaoLogin(String code);

    User join(SocialJoinRequest socialJoinRequest);
}
