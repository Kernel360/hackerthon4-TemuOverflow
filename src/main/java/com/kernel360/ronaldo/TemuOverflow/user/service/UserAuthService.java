package com.kernel360.ronaldo.TemuOverflow.user.service;

import com.kernel360.ronaldo.TemuOverflow.s3.S3Service;
import com.kernel360.ronaldo.TemuOverflow.user.dto.GetUserResponse;
import com.kernel360.ronaldo.TemuOverflow.user.dto.UserSignUpRequest;
import com.kernel360.ronaldo.TemuOverflow.user.dto.UserSignUpResponse;
import com.kernel360.ronaldo.TemuOverflow.user.entity.Role;
import com.kernel360.ronaldo.TemuOverflow.user.entity.User;
import com.kernel360.ronaldo.TemuOverflow.user.jwt.util.JwtTokenProvider;
import com.kernel360.ronaldo.TemuOverflow.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserSignUpResponse signUp(HttpServletRequest request, HttpServletResponse response, UserSignUpRequest userSignupRequest) throws Exception {

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
                .password(userSignupRequest.getPassword())
                .nickname(userSignupRequest.getNickname())
                .profileImageUrl(profileImageUrl)
                .role(Role.USER)
                .build();
        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
        UserSignUpResponse userSignUpResponse = UserSignUpResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();

        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getId());
        jwtTokenProvider.sendAccessToken(response, accessToken);

        return userSignUpResponse;
    }

    public GetUserResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return GetUserResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .id(user.getId())
                .build();
    }

    public Long getUserIdFromToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        return jwtTokenProvider.extractUserId(accessToken).orElseThrow(() -> new RuntimeException("User Id not found in token"));
    }
}