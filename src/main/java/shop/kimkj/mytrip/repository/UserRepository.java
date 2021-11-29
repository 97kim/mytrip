package shop.kimkj.mytrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
