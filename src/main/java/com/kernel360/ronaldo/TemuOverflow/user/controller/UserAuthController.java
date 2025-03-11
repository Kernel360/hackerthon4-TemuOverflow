package com.kernel360.ronaldo.TemuOverflow.user.controller;

import com.kernel360.ronaldo.TemuOverflow.user.dto.UserSignUpRequest;
import com.kernel360.ronaldo.TemuOverflow.user.dto.UserSignUpResponse;
import com.kernel360.ronaldo.TemuOverflow.user.entity.User;
import com.kernel360.ronaldo.TemuOverflow.user.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/register")
    public ResponseEntity<UserSignUpResponse> register(@RequestParam("email") String email,
                                         @RequestParam("password") String password,
                                         @RequestParam("nickname") String nickname,
                                         @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws Exception {
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest(email, password, nickname, profileImage);
        UserSignUpResponse userSignUpResponse = userAuthService.signUp(userSignUpRequest);
        return ResponseEntity.ok(userSignUpResponse);
    }

    @Operation(summary = "일반 로그인 (로그인 요청을 통해 JWT 토큰을 발급받음)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일반 로그인 성공(반환 문자열 없음. 헤더에 토큰 반환)", content = @Content(mediaType = "application/json", schema = @Schema(example = "{\n  \"message\": \"유저의 ROLE이 GUEST이므로 온보딩API를 호출해 온보딩을 진행해야합니다.\", \"role\": \"GUEST\"}"))),
            @ApiResponse(responseCode = "4XX", description = "일반 로그인 실패", content = @Content(mediaType = "application/json", schema = @Schema(example = "실패 메세지(정확히 어떤 메세지인지는 모름)")))
    })
    @PostMapping("/login")
    public String login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "로그인 요청 JSON 데이터",
                    required = true,
                    content = @Content(
                            schema = @Schema(type = "object", example = "{\"email\": \"user@example.com\", \"password\": \"password1234\"}")
                    )
            )
            @RequestBody Map<String, String> loginRequest) {
        return "로그인 성공"; // 실제 로그인 처리는 Security 필터에서 수행
    }
}
