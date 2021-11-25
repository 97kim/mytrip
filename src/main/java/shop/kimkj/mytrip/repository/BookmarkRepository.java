package shop.kimkj.mytrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.kimkj.mytrip.domain.Bookmark;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findAllByUserIdAndType(Long userId, String type);
    void deleteByContentId(String contentId);

    Bookmark findByContentIdAndUserId(String contentId, Long userId);
}

