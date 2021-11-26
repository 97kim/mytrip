package shop.kimkj.mytrip.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.kimkj.mytrip.domain.User;
import shop.kimkj.mytrip.domain.UserReview;
import shop.kimkj.mytrip.dto.UserReviewRequestDto;
import shop.kimkj.mytrip.repository.UserRepository;
import shop.kimkj.mytrip.repository.UserReviewRepository;
import shop.kimkj.mytrip.util.S3Manager;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserReviewService {

    private final UserReviewRepository userReviewRepository;
    private final UserRepository userRepository;
    private final S3Manager s3Manager;

    @Transactional
    public UserReview postUserReview(UserReviewRequestDto userReviewRequestDto, MultipartFile multipartFile) throws IOException {
        User user = userRepository.findById(userReviewRequestDto.getUserId()).orElseThrow(
                () -> new NullPointerException("해당 User 없음")
        );
        UserReview userReview = new UserReview(userReviewRequestDto, user);
        Long reviewId = userReviewRequestDto.getId();
        if (reviewId != null && multipartFile == null) { // 수정할 때 사진 선택하지 않으면 기존에 등록했던 이미지 적용
            UserReview originReview = userReviewRepository.getById(reviewId);
            userReview.setReviewImgUrl(originReview.getReviewImgUrl());
            userReviewRepository.save(userReview);
            return userReview;
        }
        if (multipartFile == null) { // 처음 등록할 때 사진 선택하지 않으면 기본 이미지 저장
            userReview.setReviewImgUrl("https://dk9q1cr2zzfmc.cloudfront.net/img/noImage.png");
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

    public List<UserReview> getUserReviews() {
        return userReviewRepository.findAll();
    }

    @Transactional
    public String deleteUserReview(Long reviewId) {
        UserReview userReview = userReviewRepository.getById(reviewId);
        String reviewImgUrl = userReview.getReviewImgUrl(); // userReview에서 이미지 url 가져옴
        s3Manager.delete(reviewImgUrl); // s3에 해당 이미지 있으면 삭제
        userReviewRepository.deleteById(reviewId); // DB에서 리뷰 삭제
        return "삭제를 완료했습니다.";
    }

}
