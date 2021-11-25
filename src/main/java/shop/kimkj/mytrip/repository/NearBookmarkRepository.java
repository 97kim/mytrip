package shop.kimkj.mytrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.kimkj.mytrip.domain.NearBookmark;

import java.util.List;

public interface NearBookmarkRepository extends JpaRepository<NearBookmark, Long> {
    List<NearBookmark> findAllByUserId(Long userId);
    void deleteByContentId(String contentId);

    NearBookmark findByContentIdAndUserId(String contentId, Long userId);
}