package shop.kimkj.mytrip.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PopularBookmarkService {
    private final PopularBookmarkRepository popularBookmarkRepository;
    private final UserRepository userRepository;

    public List<PopularBookmark> findBookmarks(Long userId) {
        return popularBookmarkRepository.findAllByUserId(userId);
    }

    @Transactional
    public void deletePopularBookmark(String contentId) {
        popularBookmarkRepository.deleteByContentId(contentId);
    }

    @Transactional
    public void savePopularBookmark(PopularBookmarkDto popularBookmarkDto) {
        User user = userRepository.findById(popularBookmarkDto.getUserId()).orElseThrow(
                () -> new NullPointerException("해당 User 없음")
        );
        PopularBookmark popularBookmark = new PopularBookmark(popularBookmarkDto, user);
        popularBookmarkRepository.save(popularBookmark);
    }

    public PopularBookmark checkPopularBookmarkStatus(String contentId, Long userId) {
        return popularBookmarkRepository.findByContentIdAndUserId(contentId, userId);
    }

}
