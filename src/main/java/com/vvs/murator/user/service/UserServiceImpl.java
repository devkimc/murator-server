package com.vvs.murator.user.service;

import com.vvs.murator.auth.jwt.JwtService;
import com.vvs.murator.feign.kakao.KakaoFeignClient;
import com.vvs.murator.feign.kakao.KakaoUserInfo;
import com.vvs.murator.user.domain.SocialType;
import com.vvs.murator.user.domain.User;
import com.vvs.murator.user.domain.UserType;
import com.vvs.murator.user.dto.KakaoLoginResponse;
import com.vvs.murator.user.dto.SocialJoinRequest;
import com.vvs.murator.user.dto.TokenDto;
import com.vvs.murator.user.exception.LoginFailException;
import com.vvs.murator.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private static final String BEARER = "Bearer ";
    private static final String grantType = "authorization_code";

    private final KakaoFeignClient kakaoFeignClient;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    @Value("${app.kakao.client-id}")
    private String clientId;

    @Value("${app.kakao.redirect-uri}")
    private String redirectUri;

    @Override
    @Transactional
    public KakaoLoginResponse kakaoLogin(String code) {

        // 1. 카카오 로그인에 성공한 유저의 액세스 토큰을 요청한다.
        Map<String, Object> token = getKakaoToken(grantType, clientId, code, redirectUri);

        String accessToken = BEARER + token.get("access_token");

        // 2. 카카오로부터 제공받은 액세스토큰으로 유저의 정보를 요청한다.
        ResponseEntity<KakaoUserInfo> kakaoUserInfo = getKakaoUserInfo(accessToken);

        KakaoUserInfo userInfo = kakaoUserInfo.getBody();

        String socialUserId = String.valueOf(userInfo.getId());
        String email = userInfo.getKakao_account().getEmail();
        String nickname = userInfo.getKakao_account().getProfile().getNickname();

        // 3. 로그인한 소셜 유저가 가입된 유저인지 확인한다.
        Optional<User> findSocialUser = userRepository.findBySocialUserId(socialUserId);

        // 3.1 만약 로그인이 안된 유저라면, 카카오로부터 제공받은 최소 정보로만 회원가입을 한다.
        User joinUser = findSocialUser.orElseGet(() -> join(
                SocialJoinRequest.builder()
                        .socialUserId(socialUserId)
                        .email(email)
                        .nickname(nickname)
                        .socialType(SocialType.KAKAO)
                        .build()
        ));

        String joinUserId = String.valueOf(joinUser.getId());

        // 4. Jwt 토큰을 생성한다. 응답 쿠키에 추가되기 위함
        TokenDto tokenDto = new TokenDto();
        tokenDto.setAccessToken(jwtService.createAccessToken(joinUserId, UserType.ROLE_GENERAL.name()));
        tokenDto.setRefreshToken(jwtService.createRefreshToken(joinUserId));

        return new KakaoLoginResponse(socialUserId, email, nickname, SocialType.KAKAO, tokenDto);
    }

    @Override
    @Transactional
    public User join(SocialJoinRequest socialJoinRequest) {

        User user = User.builder()
                .socialUserId(socialJoinRequest.getSocialUserId())
                .email(socialJoinRequest.getEmail())
                .nickname(socialJoinRequest.getNickname())
                .socialType(socialJoinRequest.getSocialType())
                .userType(UserType.ROLE_GENERAL)
                .build()
                ;

        return userRepository.save(user);
    }

    private Map<String, Object> getKakaoToken(String grantType, String clientId, String code, String redirectUri) {
        try {
            return kakaoFeignClient.createToken(grantType, clientId, code, redirectUri);
        } catch (Exception e) {
            log.error("KAKAO CREATE TOKEN ERROR - {} ", e.getMessage());
            throw new LoginFailException("카카오 로그인 실패 code = " + code);
        }
    }

    private ResponseEntity<KakaoUserInfo> getKakaoUserInfo(String token) {
        try {
            ResponseEntity<KakaoUserInfo> userInfo = kakaoFeignClient.getUserInfo(new URI("https://kapi.kakao.com/v2/user/me"), token);
            return userInfo;
        } catch (Exception e) {
            log.error("KAKAO USER INFO ERROR - {} ", e.getMessage());
            throw new LoginFailException("카카오 정보 조회 실패 token = " + token);
        }
    }
}
