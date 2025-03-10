//package com.kernel360.ronaldo.TemuOverflow.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import com.kernel360.ronaldo.TemuOverflow.user.jwt.JwtAuthenticationFilter;
//import com.kernel360.ronaldo.TemuOverflow.user.jwt.JwtTokenProvider;
//import com.kernel360.ronaldo.TemuOverflow.user.login.filter.CustomJsonUsernamePasswordAuthenticationFilter;
//import com.kernel360.ronaldo.TemuOverflow.user.login.handler.LoginFailureHandler;
//import com.kernel360.ronaldo.TemuOverflow.user.login.service.LoginService;
//import com.kernel360.ronaldo.TemuOverflow.user.login.handler.LoginSuccessHandler;
//import com.kernel360.ronaldo.TemuOverflow.user.jwt.JwtUtils;
//import devkor.ontime_back.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.logout.LogoutFilter;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final LoginService loginService;
//    private final JwtTokenProvider jwtTokenProvider;
//    private final UserRepository userRepository;
//    private final ObjectMapper objectMapper;
//    private final AppleLoginService appleLoginService;
//    private final GoogleLoginService googleLoginService;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 추가
//                .formLogin(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .headers(headers -> headers
//                        .frameOptions(frameOptions -> frameOptions.disable()))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/favicon.ico", "/h2-console/**").permitAll()
//                        .requestMatchers("/sign-up").permitAll()
//                        .requestMatchers( "/swagger-ui/**").permitAll()
//                        .requestMatchers("/error").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class)
//                .addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder());
//        provider.setUserDetailsService(loginService);
//        return new ProviderManager(provider);
//    }
//
//    @Bean
//    public LoginSuccessHandler loginSuccessHandler() {
//        return new LoginSuccessHandler(jwtTokenProvider, userRepository);
//    }
//
//    @Bean
//    public LoginFailureHandler loginFailureHandler() {
//        return new LoginFailureHandler();
//    }
//
//    @Bean
//    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
//        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
//                = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
//        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
//        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
//        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
//        return customJsonUsernamePasswordLoginFilter;
//    }
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationProcessingFilter() {
//        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider, userRepository);
//        return jwtAuthenticationFilter;
//    }
//
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOriginPattern("*");
//        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
//        configuration.addAllowedHeader("*"); // 모든 헤더 허용
//        configuration.setAllowCredentials(true); // 쿠키 포함 허용
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}