package com.vvs.murator.user.dto;

import com.vvs.murator.user.domain.SocialType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginResponse {
    private String socialUserId;
    private String email;
    private String nickname;
    private SocialType socialType;
}
