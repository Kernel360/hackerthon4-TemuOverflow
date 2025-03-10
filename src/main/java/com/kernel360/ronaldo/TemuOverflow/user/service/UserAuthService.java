package com.kernel360.ronaldo.TemuOverflow.user.service;

import com.kernel360.ronaldo.TemuOverflow.user.dto.UserSignUpRequest;
import com.kernel360.ronaldo.TemuOverflow.user.entity.User;
import com.kernel360.ronaldo.TemuOverflow.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder; // 차후 스프링시큐리티에서 빈으로 등록한 후 사용해야함.

    @Transactional
    public User signUp(UserSignUpRequest userSignupRequest) throws Exception {

        if(userRepository.findByEmail(userSignupRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        if(userRepository.findByNickname(userSignupRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Nickname already in use");
        }

        /*
        여기에 s3에 이미지 저장하고, 이미지 url 반환받는 코드 작성한 다음
        User 객체 생성할 때, 이미지 url 저장하는 코드 뒤에 추가해야 함.
         */

        User user = User.builder()
                .email(userSignupRequest.getEmail())
                .password(userSignupRequest.getPassword()) // 차후 스프링시큐리티에서 passwordEncoder빈으로 등록한 뒤 비밀번호 인코딩해서 저장해야함
                .nickname(userSignupRequest.getNickname())
                .build();
        return userRepository.save(user);
    }
}
