package shop.kimkj.mytrip.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.kimkj.mytrip.domain.PopularBookmark;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.dto.PopularBookmarkDto;
import shop.kimkj.mytrip.repository.PopularBookmarkRepository;
import shop.kimkj.mytrip.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PopularBookmarkService {
    private final PopularBookmarkRepository popularBookmarkRepository;
    private final UserRepository userRepository;

    public List<PopularBookmark> findBookmarks(String username) {
        return popularBookmarkRepository.findAllByUsername(username);
    }

    @Transactional
    public void deletePopularBookmark(String contentId) {
        popularBookmarkRepository.deleteByContentId(contentId);
    }

    @Transactional
    public void savePopularBookmark(PopularBookmarkDto popularBookmarkDto) {
        User user = userRepository.findByUsername(popularBookmarkDto.getUsername()).orElseThrow(
                () -> new NullPointerException("해당 User 없음")
        );
        PopularBookmark popularBookmark = new PopularBookmark(popularBookmarkDto, user);
        popularBookmarkRepository.save(popularBookmark);
    }

    public PopularBookmark checkPopularBookmarkStatus(String contentId, String username) {
        return popularBookmarkRepository.findByContentIdAndUsername(contentId, username);
    }

}
