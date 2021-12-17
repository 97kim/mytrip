package shop.kimkj.mytrip.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.kimkj.mytrip.repository.CommentRepository;
import shop.kimkj.mytrip.repository.UserRepository;
import shop.kimkj.mytrip.repository.UserReviewRepository;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
public class CommentServiceTest {
    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserReviewRepository userReviewRepository;

    void postComment() {

    }
    void updateComment() {

    }
    void deleteComment() {

    }

}
