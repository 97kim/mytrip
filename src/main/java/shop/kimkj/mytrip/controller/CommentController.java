package shop.kimkj.mytrip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/review/comment/{reviewId}") // 댓글 작성
    public Comment postComment(@PathVariable Long reviewId, @RequestBody CommentDto comment,
                               @AuthenticationPrincipal UserDetailsImpl nowUser) {
        return commentService.postComment(reviewId, comment, nowUser);
    }

    @GetMapping("/review/comment/{reviewId}") // 댓글 보여주기
    public List<Comment> getComment(@PathVariable Long reviewId) {
        return commentService.getComment(reviewId);
    }


    @DeleteMapping("/review/comment/{commentId}") // 댓글 삭제하기
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        return commentService.deleteComment(commentId, nowUser);
    }
}

