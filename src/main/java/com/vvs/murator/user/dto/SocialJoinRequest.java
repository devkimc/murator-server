package com.vvs.murator.user.dto;

import com.vvs.murator.user.domain.SocialType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SocialJoinRequest {
    private String socialUserId;
    private String email;
    private String nickname;
    private SocialType socialType;
}
