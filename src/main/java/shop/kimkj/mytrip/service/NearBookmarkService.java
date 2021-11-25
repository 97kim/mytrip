package shop.kimkj.mytrip.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.kimkj.mytrip.domain.NearBookmark;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.dto.NearBookmarkDto;
import shop.kimkj.mytrip.repository.NearBookmarkRepository;
import shop.kimkj.mytrip.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NearBookmarkService {
    private final NearBookmarkRepository nearBookmarkRepository;
    private final UserRepository userRepository;

    public List<NearBookmark> findAllByUserId(Long userId) {
        return nearBookmarkRepository.findAllByUserId(userId);
    }

    @Transactional
    public void saveNearBookmark(NearBookmarkDto nearBookmarkDto) {
        User user = userRepository.findByUsername(nearBookmarkDto.getUsername()).orElseThrow(
                () -> new NullPointerException("해당 User 없음")
        );
        NearBookmark nearBookmark = new NearBookmark(nearBookmarkDto, user);
        nearBookmarkRepository.save(nearBookmark);
    }

}
