package com.vvs.murator.auth;

import com.vvs.murator.user.domain.User;
import com.vvs.murator.user.exception.UserNotFoundException;
import com.vvs.murator.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;



@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 유저입니다."));
        return new UserAdapter(user);
    }
}
