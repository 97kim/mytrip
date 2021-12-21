package shop.kimkj.mytrip.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(description = "댓글 생성, 로그인 필요", method = "POST")
    @PostMapping("/reviews/{reviewId}/comment") // 댓글 작성
    public Comment postComment(@PathVariable Long reviewId, @RequestBody CommentDto comment,
                               @AuthenticationPrincipal UserDetailsImpl nowUser) {
        return commentService.postComment(reviewId, comment, nowUser);
    }

    @Operation(description = "댓글 조회", method = "GET")
    @GetMapping("/reviews/{reviewId}/comments") // 댓글 보여주기
    public List<Comment> getComment(@PathVariable Long reviewId) {
        return commentService.getComment(reviewId);
    }

    @Operation(description = "댓글 수정, 로그인 필요", method = "PUT")
    @PutMapping("/reviews/{reviewId}/comments/{commentId}") // 댓글 수정하기
    public Comment updateComment(@PathVariable Long commentId, @PathVariable Long reviewId, @RequestBody CommentDto comment,
                                 @AuthenticationPrincipal UserDetailsImpl nowUser) {
        return commentService.updateComment(reviewId, commentId, comment, nowUser);
    }

    @Operation(description = "댓글 삭제, 로그인 필요", method = "DELETE")
    @DeleteMapping("/reviews/{reviewId}/comments/{commentId}") // 댓글 삭제하기
    public void deleteComment(@PathVariable Long commentId, @PathVariable Long reviewId,
                                           @AuthenticationPrincipal UserDetailsImpl nowUser) {
        commentService.deleteComment(reviewId, commentId, nowUser);
    }
}
