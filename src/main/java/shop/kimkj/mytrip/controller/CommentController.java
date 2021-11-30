package shop.kimkj.mytrip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.kimkj.mytrip.domain.Comment;
import shop.kimkj.mytrip.dto.CommentDto;
import shop.kimkj.mytrip.security.UserDetailsImpl;
import shop.kimkj.mytrip.service.CommentService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/userReview/comment/{reviewId}") // 댓글 작성
    public Comment postComment(@PathVariable Long reviewId, @RequestBody CommentDto comment,
                               @AuthenticationPrincipal UserDetailsImpl nowUser) {
        return commentService.postComment(reviewId, comment, nowUser);
    }

    @GetMapping("/userReview/comment/{reviewId}") // 댓글 보여주기
    public List<Comment> getComment(@PathVariable Long reviewId) {
        return commentService.getComment(reviewId);
    }


    @DeleteMapping("/userReview/comment/{commentId}") // 댓글 삭제하기
    public String deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }
}

