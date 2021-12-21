package shop.kimkj.mytrip.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
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

    @Transactional
    public Comment updateComment(Long reviewId, Long commentId, CommentDto commentDto, UserDetailsImpl nowUser) {
        userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다."));

        if (!nowUser.getId().equals(comment.getUser().getId())) { // 리뷰 작성자랑 로그인한 유저랑 다르면
            throw new AccessDeniedException("권한이 없습니다.");
        }
        comment.setComment(commentDto.getComment());
        commentRepository.save(comment);

        return comment;
    }

    @Transactional
    public void deleteComment(Long reviewId, Long commentId, UserDetailsImpl nowUser) {
        userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다."));

        if (!nowUser.getId().equals(comment.getUser().getId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
}