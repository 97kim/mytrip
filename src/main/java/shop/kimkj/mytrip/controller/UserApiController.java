package shop.kimkj.mytrip.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.dto.JwtResponse;
import shop.kimkj.mytrip.dto.UserDto;
import shop.kimkj.mytrip.security.UserDetailsImpl;
import shop.kimkj.mytrip.service.UserService;
import shop.kimkj.mytrip.util.JwtTokenUtil;

import java.io.IOException;

@RequiredArgsConstructor
@RestController

public class UserApiController {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @Operation(description = "유저 삭제", method = "DELETE")
    @DeleteMapping("/deleteUser")
    public void deleteUser(@AuthenticationPrincipal UserDetailsImpl nowUser) {
        userService.deleteUser(nowUser);
    }

    @Operation(description = "로그인, 회원가입", method = "POST")
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) throws Exception {
        if (userDto.getLoginCheck().equals("signup")) {
            userService.registerUser(userDto); // 사용자 등록
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }

    @Operation(description = "회원가입 시 아이디 유효성 검사", method = "POST")
    @PostMapping("/signup/check")
    public String checkUser(@RequestBody UserDto userDto) { // UserDto에 password 안 쓰이는 거 고민해보기
        JSONObject response = new JSONObject();
        try {
            userService.checkExist(userDto);
        } catch (IllegalArgumentException e) {
            response.put("exists", Boolean.TRUE);
            return response.toString();
        }
        response.put("exists", Boolean.FALSE);
        return response.toString();
    }

    @Operation(description = "프로필 설정, 로그인 필요", method = "POST")
    @PostMapping("/profile")
    public User updateProfile(@RequestPart(required = false) String nickname,
                              @RequestPart(name = "profileImgUrl", required = false) MultipartFile multipartFile,
                              @AuthenticationPrincipal UserDetailsImpl nowUser) throws IOException {
        return userService.updateProfile(nickname, multipartFile, nowUser);
    }
}