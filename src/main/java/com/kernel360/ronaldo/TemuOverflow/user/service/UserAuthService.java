package com.kernel360.ronaldo.TemuOverflow.user.service;

import com.kernel360.ronaldo.TemuOverflow.s3.S3Service;
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
    private final S3Service s3Service;
//    private final PasswordEncoder passwordEncoder; // 차후 스프링시큐리티에서 빈으로 등록한 후 사용해야함.

    @Transactional
    public User signUp(UserSignUpRequest userSignupRequest) throws Exception {

        if(userRepository.findByEmail(userSignupRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        if(userRepository.findByNickname(userSignupRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Nickname already in use");
        }

        String profileImageUrl = null;
        if (userSignupRequest.getProfileImage() != null && !userSignupRequest.getProfileImage().isEmpty()) {
            profileImageUrl = s3Service.uploadFile(userSignupRequest.getProfileImage());
        }


        User user = User.builder()
                .email(userSignupRequest.getEmail())
                .password(userSignupRequest.getPassword()) // 차후 스프링시큐리티에서 passwordEncoder빈으로 등록한 뒤 비밀번호 인코딩해서 저장해야함
                .nickname(userSignupRequest.getNickname())
                .profileImageUrl(profileImageUrl)
                .build();
        return userRepository.save(user);
    }
}
