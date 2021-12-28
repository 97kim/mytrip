package shop.kimkj.mytrip.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.dto.UserDto;
import shop.kimkj.mytrip.repository.UserRepository;
import shop.kimkj.mytrip.security.UserDetailsImpl;
import shop.kimkj.mytrip.util.S3Manager;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final S3Manager s3Manager;

    @Transactional
    public void registerUser(UserDto userDto) {
        String username = userDto.getUsername();

        // 비밀번호 인코딩
        String password = passwordEncoder.encode(userDto.getPassword());

        // 회원가입 시 nickname은 username으로 설정
        String nickname = username;

        String profileImgUrl = "https://dk9q1cr2zzfmc.cloudfront.net/profile/default_img.png";

        User user = new User(username, password, nickname, profileImgUrl);
        userRepository.save(user);
    }

    public boolean confirmPassword(UserDto userDto) {
        Optional<User> user = userRepository.findByUsername(userDto.getUsername());
        if (Objects.equals(user, Optional.empty()) || !passwordEncoder.matches(userDto.getPassword(), user.get().getPassword())) {
            return false;
        }
        return true;
    }

    public void checkExist(UserDto userDto) {
        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(userDto.getUsername());
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }
    }

    @Transactional
    public User updateProfile(String nickname, MultipartFile multipartFile, UserDetailsImpl nowUser) throws IOException {
        User user = userRepository.findById(nowUser.getId()).orElseThrow(
                () -> new NullPointerException("해당 User 없음"));

        if (nickname != null && multipartFile != null) { // 닉네임 변경 + 사진 변경했을 때
            user.setNickname(nickname);
            String profileImgUrl = s3Manager.upload(multipartFile, "profile"); // S3 profile 폴더에 저장하고 클라우드 프론트 url 반환
            user.setProfileImgUrl(profileImgUrl);
        } else if (nickname != null) { // 닉네임만 변경했을 때
            user.setNickname(nickname);
        } else if (multipartFile != null) { // 사진만 변경했을 때
            String profileImgUrl = s3Manager.upload(multipartFile, "profile"); // S3 profile 폴더에 저장하고 클라우드 프론트 url 반환
            user.setProfileImgUrl(profileImgUrl);
        }
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void deleteUser(UserDetailsImpl nowUser) {
        userRepository.deleteById(nowUser.getUser().getId());
    }
}