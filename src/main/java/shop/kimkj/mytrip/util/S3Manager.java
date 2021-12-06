package shop.kimkj.mytrip.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class S3Manager {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket; // S3 버킷 이름

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 변환 실패"));

        return upload(uploadFile, dirName);
    }

    private String upload(File uploadFile, String dirName) {
        // S3에 저장되는 파일 이름(랜덤 uuid + 파일명)
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();
        putS3(uploadFile, fileName);
        removeNewFile(uploadFile); // 로컬에 생성된 File 삭제 (MultipartFile -> File 변환해서 로컬에 파일 생성)
        String cloudFrontUrl = "https://dk9q1cr2zzfmc.cloudfront.net/" + fileName; // AWS 클라우드 프론트로 접근할 것임
        return cloudFrontUrl; // 업로드된 파일의 S3 URL 주소 반환
    }

    private void putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead)); // PublicRead 권한으로 업로드
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("로컬에 저장된 파일 삭제 완료");
        } else {
            log.info("로컬에 저장된 파일 삭제 실패");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    public void delete(String reviewImgUrl) { // S3 파일 삭제
        String filePath = reviewImgUrl.replace("https://dk9q1cr2zzfmc.cloudfront.net/", "");
        // Delete 객체 생성
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, filePath);
        // 삭제
        amazonS3Client.deleteObject(deleteObjectRequest);
    }

}
