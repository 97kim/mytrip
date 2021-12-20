package shop.kimkj.mytrip.dto;

import lombok.Getter;

@Getter
public class UserDto {
    private String loginCheck;
    private String username;
    private String password;

    public UserDto(String loginCheck, String username, String password) {
        this.loginCheck = loginCheck;
        this.username = username;
        this.password = password;
    }
}
