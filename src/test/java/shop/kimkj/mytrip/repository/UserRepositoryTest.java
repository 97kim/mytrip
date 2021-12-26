package shop.kimkj.mytrip.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.kimkj.mytrip.domain.User;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저가 정상적으로 저장되는지 확인")
    public void saveUser() {
        //given
        User user = new User("testId", "testPassword", "testNickname", "http://placeimg.com/640/480/nature");

        //when
        User savedUser = userRepository.save(user);

        //then
        assertThat(user).isSameAs(savedUser);
        assertThat(user.getUsername()).isEqualTo(savedUser.getUsername());
        assertThat(user.getPassword()).isEqualTo(savedUser.getPassword());
        assertThat(user.getNickname()).isEqualTo(savedUser.getNickname());
        assertThat(user.getProfileImgUrl()).isEqualTo(savedUser.getProfileImgUrl());
        assertThat(userRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("유저가 정상적으로 조회되는지 확인")
    public void findUser() {
        //given
        User savedUser = userRepository.save(new User("testId", "testPassword", "testNickname", "http://placeimg.com/640/480/nature"));
        User savedUser2 = userRepository.save(new User("testId2", "testPassword2", "testNickname2", "http://placeimg.com/640/480/people"));

        //when
        User findUser = userRepository.findById(savedUser.getId())
                .orElseThrow(() -> new NullPointerException("해당 User 없음"));
        User findUser2 = userRepository.findById(savedUser2.getId())
                .orElseThrow(() -> new NullPointerException("해당 User 없음"));

        //then
        assertThat(userRepository.count()).isEqualTo(2);
        assertThat(findUser.getUsername()).isEqualTo("testId");
        assertThat(findUser.getNickname()).isEqualTo("testNickname");
        assertThat(findUser.getProfileImgUrl()).isEqualTo("http://placeimg.com/640/480/nature");
        assertThat(findUser2.getUsername()).isEqualTo("testId2");
        assertThat(findUser2.getNickname()).isEqualTo("testNickname2");
        assertThat(findUser2.getProfileImgUrl()).isEqualTo("http://placeimg.com/640/480/people");
    }

    @Test
    @DisplayName("유저가 정상적으로 수정되는지 확인")
    public void updateUser() {
        //given
        User savedUser = userRepository.save(new User("testId", "testPassword", "testNickname", "http://placeimg.com/640/480/nature"));

        //when
        User findUser = userRepository.findById(savedUser.getId())
                .orElseThrow(() -> new NullPointerException("해당 User 없음"));
        findUser.setNickname("testNickname2");
        findUser.setProfileImgUrl("http://placeimg.com/640/480/people");
        User updatedUser = userRepository.save(findUser);

        //then
        assertThat(userRepository.count()).isEqualTo(1);
        assertThat(savedUser.getUsername()).isEqualTo(updatedUser.getUsername());
        assertThat(savedUser.getPassword()).isEqualTo(updatedUser.getPassword());
        assertThat(updatedUser.getNickname()).isEqualTo("testNickname2");
        assertThat(updatedUser.getProfileImgUrl()).isEqualTo("http://placeimg.com/640/480/people");
    }

    @Test
    @DisplayName("유저가 정상적으로 삭제되는지 확인")
    public void deleteUser() {
        //given
        User savedUser = userRepository.save(new User("testId", "testPassword", "testNickname", "http://placeimg.com/640/480/nature"));

        //when
        userRepository.deleteById(savedUser.getId());

        //then
        assertThat(userRepository.count()).isEqualTo(0);
    }

}