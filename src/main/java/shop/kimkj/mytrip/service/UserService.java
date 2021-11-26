package shop.kimkj.mytrip.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.dto.UserDto;
import shop.kimkj.mytrip.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public Long registerUser(UserDto userDto) {
        String username = userDto.getUsername();

        // 비밀번호 인코딩
        String password = passwordEncoder.encode(userDto.getPassword());

        // 회원가입 시 nickname은 username으로 설정
        String nickname = username;

        String profileImgUrl = "https://dk9q1cr2zzfmc.cloudfront.net/profile/default_img.png";

        User user = new User(username, password, nickname, profileImgUrl);
        User registeredUser = userRepository.save(user);

        return registeredUser.getId();
    }

    public void checkExist(UserDto userDto) {
        String username = userDto.getUsername();

        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }
    }

    public Long getUserId(UserDto userDto) {
        User user = userRepository.getByUsername(userDto.getUsername());
        return user.getId();
    }

}