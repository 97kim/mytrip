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
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final S3Manager s3Manager;

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
        // 기본적으로 JPA save 연산은 변경된 필드 값만 update 하는 것이 아니고 변경되지 않은 필드 값에는 null이 들어간다.
        if (nickname == null) { // 닉네임을 변경하지 않을 때
            user.setNickname(user.getNickname()); // 닉네임을 변경하지 않을 때에는 null을 넣지 않기 위해 기존 닉네임을 넣어준다.
        } else if (multipartFile == null) { // 사진이 선택되지 않았을 때
            user.setProfileImgUrl(user.getProfileImgUrl()); // 사진을 선택하지 않았을 때에는 null을 넣지 않기 위해 기존 이미지를 넣어준다.
        } else { // 닉네임 수정 + 사진 선택하면 S3에 저장하고 DB에 클라우드 프론트 url로 수정
            user.setNickname(nickname);
            String profileImgUrl = s3Manager.upload(multipartFile, "profile");// 클라우드 프론트 url
            user.setProfileImgUrl(profileImgUrl);
        }
        userRepository.save(user);
        return user;
    }
}