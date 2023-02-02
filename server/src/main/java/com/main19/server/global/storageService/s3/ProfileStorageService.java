package com.main19.server.global.storageService.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.main19.server.global.exception.BusinessLogicException;
import com.main19.server.global.exception.ExceptionCode;
import com.main19.server.domain.member.entity.Member;
import com.main19.server.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProfileStorageService extends S3StorageService{
    private final AmazonS3 s3Client;
    private final MemberService memberService;
    public String uploadProfileImage(MultipartFile profileImage, long memberId) {
        Member findMember = memberService.findMember(memberId);
        if (!findMember.getProfileImage().equals("https://s3.ap-northeast-2.amazonaws.com/main19-bucket/member/profileImage/5ce172e0-35c9-4453-bba2-6b97af732a36.png")) {
            removeProfileImage(memberId);
        }

        String profileImageUrl;

        String fileName = createFileName(profileImage.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(profileImage.getSize());
        objectMetadata.setContentType(profileImage.getContentType());

        try(InputStream inputStream = profileImage.getInputStream()) {
            s3Client.putObject(new PutObjectRequest(bucket + "/member/profileImage", fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            profileImageUrl = (s3Client.getUrl(bucket + "/member/profileImage", fileName).toString());
        } catch(IOException e) {
            throw new BusinessLogicException(ExceptionCode.MEDIA_UPLOAD_ERROR);
        }
        return  profileImageUrl;
    }

    public void removeProfileImage(long memberId) {
        Member findMember = memberService.findMember(memberId);
        if (findMember.getProfileImage().equals("https://s3.ap-northeast-2.amazonaws.com/main19-bucket/member/profileImage/5ce172e0-35c9-4453-bba2-6b97af732a36.png")) {
            throw new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND);
        }

        String fileName = (findMember.getProfileImage()).substring(74);

        if (!s3Client.doesObjectExist(bucket + "/member/profileImage", fileName)) {
            throw new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND);
        }
        s3Client.deleteObject(bucket + "/member/profileImage", fileName);
    }
}
