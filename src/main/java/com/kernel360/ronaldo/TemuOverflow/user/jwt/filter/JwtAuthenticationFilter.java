package com.kernel360.ronaldo.TemuOverflow.user.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kernel360.ronaldo.TemuOverflow.user.entity.User;
import com.kernel360.ronaldo.TemuOverflow.user.jwt.util.JwtTokenProvider;
import com.kernel360.ronaldo.TemuOverflow.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

//    private static final List<String> NO_CHECK_URLS = List.of("/api/login", "/api/register", "/swagger-ui", "/v3/api-docs", "/api/chat","/api/reply/ai/", "/api/reply/post", "/api/article", "api/article/search");

    private static final Map<String, List<String>> NO_CHECK_METHODS = Map.of(
            "/api/login", List.of("POST"),
            "/api/register", List.of("POST"),
            "/api/chat", List.of("POST"),
            "/api/reply/ai/", List.of("POST"),
            "/api/reply/post", List.of("GET"),
            "/api/article", List.of("GET"),
            "/api/article/search", List.of("GET"),
            "/swagger-ui", List.of("GET"),
            "/v3/api-docs", List.of("GET")
    );

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String method = request.getMethod(); // HTTP 메서드 확인
        log.info("Request URI: {} | Method: {}", requestURI, method);
        try {
            log.info("Attempting to authenticate user {}", requestURI);
            if (NO_CHECK_METHODS.entrySet().stream()
                    .anyMatch(entry -> requestURI.startsWith(entry.getKey()) && entry.getValue().contains(method))) {
                log.info("Skipping authentication for user {}", requestURI);
                filterChain.doFilter(request, response);
                log.info("다음 필터로 넘어 갔음! {}", requestURI);
                return;
            }
            log.info("노체크 url 조건문에 들어가지 않음", requestURI);

            String accessToken= jwtTokenProvider.extractAccessToken(request)
                    .orElse(null);

            // 엑세스 토큰이 있고, 유효할 경우 checkAccessTokenAndAuthentication 메서드 호출해 권한정보 저장하고 스프링 시큐리티 필터체인 계속 진행
            if (accessToken != null && jwtTokenProvider.isTokenValid(accessToken)) {
                checkAccessTokenAndAuthentication(request, response, filterChain);
            }

            // 엑세스 토큰이 없는 경우 Exception 발생
            if (accessToken == null) {
                throw new IllegalArgumentException("Empty Access token!~!");
            }


        }
        catch (IllegalArgumentException ex) {
            handleInvalidTokenException(response, ex);
        }

    }

    // accessToken으로 유저의 권한정보만 저장하고 인증 허가(스프링 시큐리티 필터체인 中 인증체인 통과해 다음 체인으로 이동)
    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");
        jwtTokenProvider.extractAccessToken(request)
                .ifPresent(accessToken -> {
                    jwtTokenProvider.extractEmail(accessToken)
                            .ifPresent(email -> userRepository.findByEmail(email)
                                    .ifPresent(this::saveAuthentication)
                            );

                    jwtTokenProvider.extractUserId(accessToken)
                            .ifPresent(userId -> log.info("추출된 userId: {}", userId));
                });

        filterChain.doFilter(request, response);
    }

    // 인증 허가
    public void saveAuthentication(User myUser) {
        String password = myUser.getPassword();

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getEmail())
                .password(password)
                .roles(myUser.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // 비밀번호 랜덤 생성
    private String generateRandomPassword(int length) {
        final String CHAR_SET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()-_=+[]{}|;:,.<>?";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(CHAR_SET.length());
            password.append(CHAR_SET.charAt(index));
        }

        return password.toString();
    }

    private void handleInvalidTokenException(HttpServletResponse response, IllegalArgumentException ex) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        log.info("InvalidTokenException 발생");

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
    }
}