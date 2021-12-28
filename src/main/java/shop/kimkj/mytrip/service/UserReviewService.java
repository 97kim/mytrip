package shop.kimkj.mytrip.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.domain.UserReview;
import shop.kimkj.mytrip.domain.UserReviewLikes;
import shop.kimkj.mytrip.dto.UserReviewDto;
import shop.kimkj.mytrip.repository.UserRepository;
import shop.kimkj.mytrip.repository.UserReviewLikeRepository;
import shop.kimkj.mytrip.repository.UserReviewRepository;
import shop.kimkj.mytrip.security.UserDetailsImpl;
import shop.kimkj.mytrip.util.S3Manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserReviewService {

    private final UserReviewRepository userReviewRepository;
    private final UserRepository userRepository;
    private final UserReviewLikeRepository userReviewLikeRepository;
    private final S3Manager s3Manager;

    @Transactional
    public UserReview putUserReview(Long reviewId, UserReviewDto userReviewRequestDto, MultipartFile multipartFile, UserDetailsImpl nowUser) throws IOException {
        UserReview editReview = userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));
        editReview.setTitle(userReviewRequestDto.getTitle());
        editReview.setPlace(userReviewRequestDto.getPlace());
        editReview.setReview(userReviewRequestDto.getReview());

        if (!nowUser.getId().equals(getUserReview(reviewId).getUser().getId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        // 작성자인 경우
        if (multipartFile == null) { // 수정할 때 사진 선택하지 않으면 기존에 등록했던 이미지 적용
            UserReview originReview = userReviewRepository.findById(reviewId).orElseThrow(
                    () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));
            editReview.setReviewImgUrl(originReview.getReviewImgUrl());
            userReviewRepository.save(editReview);
            return editReview;
        }

        String reviewImgUrl = s3Manager.upload(multipartFile, "reviewImg"); // 클라우드 프론트 url
        editReview.setReviewImgUrl(reviewImgUrl);
        userReviewRepository.save(editReview);
        return editReview;
    }

    @Transactional
    public UserReview postUserReview(UserReviewDto userReviewDto, MultipartFile multipartFile, UserDetailsImpl nowUser) throws IOException {
        User user = userRepository.findById(nowUser.getId()).orElseThrow(
                () -> new NullPointerException("해당 User 없음")
        );

        UserReview userReview = new UserReview(userReviewDto, user);

        if (multipartFile == null) { // 처음 등록할 때 사진 선택하지 않으면 기본 이미지 저장
            userReview.setReviewImgUrl("https://dk9q1cr2zzfmc.cloudfront.net/img/default.jpg");
        } else { // 사진 선택하면 S3에 저장 + DB에 클라우드 프론트 url 저장
            String reviewImgUrl = s3Manager.upload(multipartFile, "reviewImg"); // 클라우드 프론트 url
            userReview.setReviewImgUrl(reviewImgUrl);
        }
        userReviewRepository.save(userReview);
        return userReview;
    }

    public UserReview getUserReview(Long reviewId) {
        return userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다.")
        );
    }

    @Transactional
    public void deleteUserReview(Long reviewId, UserDetailsImpl nowUser) {
        UserReview userReview = userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));

        if (!nowUser.getId().equals(getUserReview(reviewId).getUser().getId())) { // 리뷰 작성자랑 로그인한 유저랑 다르면
            throw new AccessDeniedException("권한이 없습니다.");
        }

        String reviewImgUrl = userReview.getReviewImgUrl(); // userReview에서 이미지 url 가져옴

        if (!reviewImgUrl.equals("https://dk9q1cr2zzfmc.cloudfront.net/img/default.jpg")) { // 기본 이미지가 아닐 때만 S3에서 삭제
            s3Manager.delete(reviewImgUrl); // s3에 해당 이미지 있으면 삭제
        }
        userReviewRepository.deleteById(reviewId); // DB에서 리뷰 삭제
    }

    @Transactional
    public void deleteLike(Long reviewId, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        UserReview userReview = userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰 없음")
        );

        userReview.setLikeCnt(userReview.getLikeCnt() - 1);
        userReviewLikeRepository.deleteByUserReviewIdAndUserId(reviewId, nowUser.getId());
    }

    @Transactional
    public UserReviewLikes saveLike(Long reviewId, UserDetailsImpl nowUser) {
        UserReview userReview = userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰 없음")
        );
        UserReviewLikes userReviewLikes = new UserReviewLikes(userReview, nowUser.getUser());
        userReview.setLikeCnt(userReview.getLikeCnt() + 1);
        userReviewLikeRepository.save(userReviewLikes);
        return userReviewLikes;
    }

    public Map<String, Boolean> checkLikeStatus(Long reviewId, Long userId) {
        Map<String, Boolean> response = new HashMap<>();
        UserReviewLikes userReviewLikes = userReviewLikeRepository.findByUserReviewIdAndUserId(reviewId, userId);
        if (userReviewLikes == null) {
            response.put("likeStatus", Boolean.FALSE);
        } else {
            response.put("likeStatus", Boolean.TRUE);
        }
        return response;
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