package shop.kimkj.mytrip.domain;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // @Order로 순서 정하는 방법
class UserTest {

    @Test
    @DisplayName("user 생성")
    @Order(1)
    public void createUser() {
        //given
        String username = "testUser";
        String password = "testPassword";
        String nickname = "testNickname";
        String profileImgUrl = "http://placeimg.com/640/480/nature";

        //when
        User user = new User(username, password, nickname, profileImgUrl);

        //then
        assertThat(user.getId()).isNull();
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getProfileImgUrl()).isEqualTo(profileImgUrl);
    }

    @Test
    @DisplayName("user 여러 명 생성")
    @Order(2)
    public void createUsers() {
        //given
        User user = new User("testUser", "testPassword", "testNickname", "http://placeimg.com/640/480/nature");
        User user2 = new User("testUser2", "testPassword2", "testNickname2", "http://placeimg.com/640/480/nature");
        User user3 = new User("testUser3", "testPassword3", "testNickname3", "http://placeimg.com/640/480/nature");

        //when
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);

        //then
        assertThat(users.size()).isEqualTo(31);
    }
}