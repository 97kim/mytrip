package shop.kimkj.mytrip.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class EbHealthCheck {

    // EB 헬스체크를 위한 코드
    @GetMapping(value = "/")
    public ResponseEntity<?> doHealthCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
