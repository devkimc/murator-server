package com.vvs.murator.auth;

import com.vvs.murator.user.domain.User;
import com.vvs.murator.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;


@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
                // TODO: 에러 추가 후 수정
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        return new UserAdapter(user);
    }
}
