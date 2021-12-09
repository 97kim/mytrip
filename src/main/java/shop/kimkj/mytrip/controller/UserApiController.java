package shop.kimkj.mytrip.controller;

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

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) throws Exception {
        if (userDto.getLoginCheck().equals("signup")) {
            userService.registerUser(userDto); // 사용자 등록
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }

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

    @PostMapping("/profile")
    public User updateProfile(@RequestPart(required = false) String nickname,
                              @RequestPart(name = "profileImgUrl", required = false) MultipartFile multipartFile,
                              @AuthenticationPrincipal UserDetailsImpl nowUser) throws IOException {
        return userService.updateProfile(nickname, multipartFile, nowUser);
    }
}