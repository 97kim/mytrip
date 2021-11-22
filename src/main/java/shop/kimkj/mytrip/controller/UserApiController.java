package shop.kimkj.mytrip.controller;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import shop.kimkj.mytrip.dto.JwtResponse;
import shop.kimkj.mytrip.dto.UserDto;
import shop.kimkj.mytrip.service.UserService;
import shop.kimkj.mytrip.util.JwtTokenUtil;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;


    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDto userDto) throws Exception {
        authenticate(userDto.getUsername(), userDto.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }

    @PostMapping(value = "/sign-up/save")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) throws Exception {
        userService.registerUser(userDto);
        authenticate(userDto.getUsername(), userDto.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }

//    @PostMapping("/sign-up/check-dup")
//    public Map<String, Boolean> checkUser(@RequestBody UserDto userDto) {
//        Map<String, Boolean> response = new HashMap<>();
//        try {
//            userService.checkExist(userDto);
//        } catch (IllegalArgumentException e) {
//            response.put("exists", Boolean.TRUE);
//            return response;
//        }
//        response.put("exists", Boolean.FALSE);
//        return response;
//    }

    @PostMapping("/sign-up/check-dup")
    public String checkUser(@RequestBody UserDto userDto) {
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

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}