package com.vvs.murator.user.controller;

import com.vvs.murator.auth.CookieUtils;
import com.vvs.murator.auth.jwt.TokenType;
import com.vvs.murator.common.ApiResult;
import com.vvs.murator.common.ApiUtils;
import com.vvs.murator.user.dto.KakaoLoginResponse;
import com.vvs.murator.user.dto.SocialLoginResponse;
import com.vvs.murator.user.dto.TokenDto;
import com.vvs.murator.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {


    @Value("${domain.host}")
    private String domain;

    private final UserService userService;

    @PostMapping("/kakao")
    public ApiResult<SocialLoginResponse> kakaoLogin(@RequestParam("code") String code, HttpServletResponse response) {
        KakaoLoginResponse kakaoLoginResponse = userService.kakaoLogin(code);

        SocialLoginResponse socialLoginResponse = SocialLoginResponse.builder()
                .socialUserId(kakaoLoginResponse.getSocialUserId())
                .email(kakaoLoginResponse.getEmail())
                .nickname(kakaoLoginResponse.getNickname())
                .socialType(kakaoLoginResponse.getSocialType())
                .build();

        if (kakaoLoginResponse.getTokenDto() != null) {
            TokenDto tokenDto = kakaoLoginResponse.getTokenDto();

            CookieUtils.addCookie(response, TokenType.ACCESS_TOKEN.name(), tokenDto.getAccessToken(), domain);
            CookieUtils.addCookie(response, TokenType.REFRESH_TOKEN.name(), tokenDto.getRefreshToken(), domain);

            return ApiUtils.success(socialLoginResponse);
        }

        return ApiUtils.fail(socialLoginResponse);
    }
}
