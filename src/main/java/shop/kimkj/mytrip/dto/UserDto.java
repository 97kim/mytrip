package shop.kimkj.mytrip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    private String loginCheck;
    private String username;
    private String password;
}
