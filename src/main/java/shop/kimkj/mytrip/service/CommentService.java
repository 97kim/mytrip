package shop.kimkj.mytrip.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shop.kimkj.mytrip.domain.Comment;
import shop.kimkj.mytrip.domain.UserReview;
import shop.kimkj.mytrip.dto.CommentDto;
import shop.kimkj.mytrip.repository.CommentRepository;
import shop.kimkj.mytrip.repository.UserReviewRepository;
import shop.kimkj.mytrip.security.UserDetailsImpl;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserReviewRepository userReviewRepository;

    public Comment postComment(Long reviewId, CommentDto commentDto, UserDetailsImpl nowUser) {
        UserReview userReview = userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));

        Comment comment = new Comment(commentDto, userReview, nowUser.getUser());
        commentRepository.save(comment);
        return comment;
    }

    public List<Comment> getComment(Long reviewId) {
        return commentRepository.findAllByUserReviewId(reviewId);
    }

    public String deleteComment(Long CommentId) {
        commentRepository.deleteById(CommentId);
        return "삭제를 완료했습니다.";
    }
}
