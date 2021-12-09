package shop.kimkj.mytrip.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.domain.UserReview;
import shop.kimkj.mytrip.domain.UserReviewLikes;
import shop.kimkj.mytrip.dto.UserReviewRequestDto;
import shop.kimkj.mytrip.repository.UserRepository;
import shop.kimkj.mytrip.repository.UserReviewLikeRepository;
import shop.kimkj.mytrip.repository.UserReviewRepository;
import shop.kimkj.mytrip.security.UserDetailsImpl;
import shop.kimkj.mytrip.util.S3Manager;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserReviewService {

    private final UserReviewRepository userReviewRepository;
    private final UserRepository userRepository;
    private final UserReviewLikeRepository userReviewLikeRepository;
    private final S3Manager s3Manager;

    @Transactional
    public ResponseEntity<?> putUserReview(Long reviewId, UserReviewRequestDto userReviewRequestDto, MultipartFile multipartFile, UserDetailsImpl nowUser) throws IOException {
        UserReview editReview = userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));
        editReview.setTitle(userReviewRequestDto.getTitle());
        editReview.setPlace(userReviewRequestDto.getPlace());
        editReview.setReview(userReviewRequestDto.getReview());

        if (!nowUser.getId().equals(getUserReview(reviewId).getUser().getId())) {
            return new ResponseEntity<>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN); // 403(FORBIDDEN)에러 - 권한없음
        }
        // 작성자인 경우
        if (multipartFile == null) { // 수정할 때 사진 선택하지 않으면 기존에 등록했던 이미지 적용
            UserReview originReview = userReviewRepository.findById(reviewId).orElseThrow(
                    () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));
            editReview.setReviewImgUrl(originReview.getReviewImgUrl());
            userReviewRepository.save(editReview);
            return ResponseEntity.ok(editReview);
        }
        String reviewImgUrl = s3Manager.upload(multipartFile, "reviewImg"); // 클라우드 프론트 url
        editReview.setReviewImgUrl(reviewImgUrl);
        userReviewRepository.save(editReview);
        return ResponseEntity.ok(editReview);
    }

    @Transactional
    public ResponseEntity<?> postUserReview(UserReviewRequestDto userReviewRequestDto, MultipartFile multipartFile, UserDetailsImpl nowUser) throws IOException {
        User user = userRepository.findById(nowUser.getId()).orElseThrow(
                () -> new NullPointerException("해당 User 없음")
        );

        UserReview userReview = new UserReview(userReviewRequestDto, user);

        if (multipartFile == null) { // 처음 등록할 때 사진 선택하지 않으면 기본 이미지 저장
            userReview.setReviewImgUrl("https://dk9q1cr2zzfmc.cloudfront.net/img/default.jpg");
        } else { // 사진 선택하면 S3에 저장 + DB에 클라우드 프론트 url 저장
            String reviewImgUrl = s3Manager.upload(multipartFile, "reviewImg"); // 클라우드 프론트 url
            userReview.setReviewImgUrl(reviewImgUrl);
        }
        userReviewRepository.save(userReview);
        return ResponseEntity.ok(userReview);
    }

    public UserReview getUserReview(Long reviewId) {
        return userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다.")
        );
    }

    @Transactional
    public ResponseEntity<?> deleteUserReview(Long reviewId, UserDetailsImpl nowUser) {
        if (!nowUser.getId().equals(getUserReview(reviewId).getUser().getId())) { // 리뷰 작성자랑 로그인한 유저랑 다르면
            return new ResponseEntity<>("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN); // 403(FORBIDDEN)에러 - 권한없음
        }
        UserReview userReview = userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));
        String reviewImgUrl = userReview.getReviewImgUrl(); // userReview에서 이미지 url 가져옴
        if (!reviewImgUrl.equals("https://dk9q1cr2zzfmc.cloudfront.net/img/default.jpg")) { // 기본 이미지가 아닐 때만 S3에서 삭제
            s3Manager.delete(reviewImgUrl); // s3에 해당 이미지 있으면 삭제
        }
        userReviewRepository.deleteById(reviewId); // DB에서 리뷰 삭제
        return new ResponseEntity<>(HttpStatus.OK); // 200 성공
    }

    @Transactional
    public void deleteLike(Long userReviewId, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        UserReview userReview = userReviewRepository.findById(userReviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰 없음")
        );

        userReview.setLikeCnt(userReview.getLikeCnt() - 1);
        userReviewLikeRepository.deleteByUserReviewIdAndUserId(userReviewId, nowUser.getId());
    }

    @Transactional
    public void saveLike(Long userReviewId, UserDetailsImpl nowUser) {
        UserReview userReview = userReviewRepository.findById(userReviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰 없음")
        );
        UserReviewLikes userReviewLikes = new UserReviewLikes(userReview, nowUser.getUser());
        userReview.setLikeCnt(userReview.getLikeCnt() + 1);
        userReviewLikeRepository.save(userReviewLikes);
    }

    public UserReviewLikes checkLikeStatus(Long userReviewId, Long userId) {
        return userReviewLikeRepository.findByUserReviewIdAndUserId(userReviewId, userId);
    }

    public List<UserReview> getUserReviews(String type) throws Exception {
        if (type.equals("like")) {
            return userReviewRepository.findAll(Sort.by(Sort.Direction.DESC, "likeCnt", "CreatedAt"));
        } else if (type.equals("date")) {
            return userReviewRepository.findAll(Sort.by(Sort.Direction.DESC, "CreatedAt"));
        } else {
            throw new Exception();
        }
    }
}