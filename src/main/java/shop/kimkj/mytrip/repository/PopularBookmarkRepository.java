package shop.kimkj.mytrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.kimkj.mytrip.domain.PopularBookmark;

import java.util.List;

public interface PopularBookmarkRepository extends JpaRepository<PopularBookmark, Long> {
    List<PopularBookmark> findAllByUsername(String username);
    void deleteByContentId(String contentId);
    PopularBookmark findByContentIdAndUsername(String contentId, String username);
}

