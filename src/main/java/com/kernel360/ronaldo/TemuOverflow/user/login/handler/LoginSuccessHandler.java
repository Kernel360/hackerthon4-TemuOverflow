package com.kernel360.ronaldo.TemuOverflow.user.login.handler;

import com.kernel360.ronaldo.TemuOverflow.user.jwt.util.JwtTokenProvider;
import com.kernel360.ronaldo.TemuOverflow.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        log.info("login success handler 초기 진입");
        String email = extractUsername(authentication); // 인증 정보에서 Username(email) 추출
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    // email과 userId기반으로 엑세스토큰 생성
                    String accessToken = jwtTokenProvider.createAccessToken(email, user.getId());
                    jwtTokenProvider.sendAccessToken(response, accessToken);

                    log.info("로그인에 성공하였습니다. 이메일 : {}", email);
                    log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);
                    log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);

                    try {
                        // 응답 Content-Type 설정
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");

                        String msg = "로그인에 성공하셨습니다!";
                        // JSON 응답 생성
                        String responseBody = String.format(
                                "{ \"status\": \"success\", \"code\": \"200\", \"message\": \"%s\", \"data\": { " +
                                        "\"userId\": %d, \"email\": \"%s\", \"nickname\": \"%s\" } }",
                                msg, user.getId(), user.getEmail(), user.getNickname()
                        );

                        // 응답 바디에 작성
                        response.getWriter().write(responseBody);
                        response.getWriter().flush();
                    } catch (IOException e) {
                        log.error("응답 바디 작성 중 오류 발생", e);
                    }
                });
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}