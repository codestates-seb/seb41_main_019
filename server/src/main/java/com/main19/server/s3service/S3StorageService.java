package com.main19.server.s3service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.annotation.PostConstruct;

import com.main19.server.member.entity.Member;
import com.main19.server.member.service.MemberService;
import com.main19.server.posting.entity.Posting;
import com.main19.server.posting.service.PostingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.posting.entity.Media;
import com.main19.server.posting.repository.MediaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3StorageService {
	private final AmazonS3 s3Client;
	private final MediaRepository mediaRepository;
	private final MemberService memberService;
	private final PostingService postingService;

	@Value("${cloud.aws.credentials.accessKey}")
	private String accessKey;

	@Value("${cloud.aws.credentials.secretKey}")
	private String secretKey;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Value("${cloud.aws.region.static}")
	private String region;

	@PostConstruct
	public AmazonS3Client amazonS3Client() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
		return (AmazonS3Client) AmazonS3ClientBuilder.standard()
			.withRegion(region)
			.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
			.build();
	}

	public List<String> uploadMedia(List<MultipartFile> multipartFiles) {
		List<String> mediaUrlList = new ArrayList<>();

		for (MultipartFile file : multipartFiles) {
			String fileName = createFileName(file.getOriginalFilename());
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(file.getSize());
			objectMetadata.setContentType(file.getContentType());

			try(InputStream inputStream = file.getInputStream()) {
				s3Client.putObject(new PutObjectRequest(bucket + "/posting/media", fileName, inputStream, objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead));
				mediaUrlList.add(s3Client.getUrl(bucket + "/posting/media", fileName).toString());
			} catch(IOException e) {
				throw new BusinessLogicException(ExceptionCode.MEDIA_UPLOAD_ERROR);
			}
		}
		return mediaUrlList;
	}

	public void removeAll(long postingId) {
		Posting posting = postingService.findPosting(postingId);
		List<Media> findMedias = posting.getPostingMedias();

		for (Media fileMedia : findMedias) {
			Media findMedia = findVerfiedMedia(fileMedia.getMediaId());
			String fileName = (fileMedia.getMediaUrl()).substring(68);

			if (!s3Client.doesObjectExist(bucket + "/posting/media", fileName)) {
				throw new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND);
			}
			s3Client.deleteObject(bucket + "/posting/media", fileName);
		}
	}

	public void remove(long mediaId) {
		Posting posting = postingService.findVerfiedMedia(mediaId).getPosting();
		if (posting.getPostingMedias().stream().count() == 1) {
			throw new BusinessLogicException(ExceptionCode.POSTING_MEDIA_ERROR);
		}

		Media findmedia = findVerfiedMedia(mediaId);
		String fileName = (findmedia.getMediaUrl()).substring(68);

		if (!s3Client.doesObjectExist(bucket + "/posting/media", fileName)) {
			throw new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND);
		}
			s3Client.deleteObject(bucket + "/posting/media", fileName);
		}

		private Media findVerfiedMedia(long mediaId) {
			Optional<Media> optionalMedia = mediaRepository.findById(mediaId);
			Media findMedia =
				optionalMedia.orElseThrow(() ->
					new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND));

			return findMedia;
		}
		private String createFileName(String fileName) {
			return UUID.randomUUID().toString().concat(getFileExtension(fileName));
		}

		private String getFileExtension(String fileName) {
			if (fileName.length() == 0) {
				throw new BusinessLogicException(ExceptionCode.WRONG_POSTING_MEDIA);
			}

			ArrayList<String> fileValidate = new ArrayList<>();
			fileValidate.add(".jpg");
			fileValidate.add(".jpeg");
			fileValidate.add(".png");
			fileValidate.add(".JPG");
			fileValidate.add(".JPEG");
			fileValidate.add(".PNG");
			fileValidate.add(".mp4");
			String idxFileName = fileName.substring(fileName.lastIndexOf("."));
			if (!fileValidate.contains(idxFileName)) {
				throw new BusinessLogicException(ExceptionCode.WRONG_MEDIA_FORMAT);
			}
			return fileName.substring(fileName.lastIndexOf("."));
	}

	public String uploadProfileImage(MultipartFile profileImage) {
		String profileImageUrl;

		String fileName = createProfileImageName(profileImage.getOriginalFilename());
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(profileImage.getSize());
		objectMetadata.setContentType(profileImage.getContentType());

		try(InputStream inputStream = profileImage.getInputStream()) {
			s3Client.putObject(new PutObjectRequest(bucket + "/member/profileImage", fileName, inputStream, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
			profileImageUrl = (s3Client.getUrl(bucket + "/member/profileImage", fileName).toString());
			} catch(IOException  e) {
				throw new BusinessLogicException(ExceptionCode.MEDIA_UPLOAD_ERROR);
			}
		return  profileImageUrl;
	}

	public void removeProfileImage(long memberId) {
		Member findMember = memberService.findMember(memberId);
		String fileName = (findMember.getProfileImage()).substring(74);

		if (!s3Client.doesObjectExist(bucket + "/member/profileImage", fileName)) {
			throw new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND);
		}
		s3Client.deleteObject(bucket + "/member/profileImage", fileName);
	}

	private String createProfileImageName(String fileName) {
		if (fileName.length() == 0) {
			return null;
		}
		return UUID.randomUUID().toString().concat(getProfileImageExtension(fileName));
	}

	private String getProfileImageExtension(String fileName) {
		ArrayList<String> fileValidate = new ArrayList<>();
		fileValidate.add(".jpg");
		fileValidate.add(".jpeg");
		fileValidate.add(".png");
		fileValidate.add(".JPG");
		fileValidate.add(".JPEG");
		fileValidate.add(".PNG");
		String idxFileName = fileName.substring(fileName.lastIndexOf("."));
		if (!fileValidate.contains(idxFileName)) {
			throw new BusinessLogicException(ExceptionCode.WRONG_MEDIA_FORMAT);
		}
		return fileName.substring(fileName.lastIndexOf("."));
	}
}
