package com.main19.server.postings.service;

import com.main19.server.postings.entity.Media;
import com.main19.server.postings.repository.MediaRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;

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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3StorageService {
	private final AmazonS3 s3Client;
	private final MediaRepository mediaRepository;

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

	public List<String> upload(List<MultipartFile> multipartFiles) {
		List<String> mediaUrlList = new ArrayList<>();

		// forEach 구문을 통해 multipartFile로 넘어온 파일들 하나씩 fileNameList에 추가
		for (MultipartFile file : multipartFiles) {
			String fileName = createFileName(file.getOriginalFilename());
			// 지울거 System.out.println(fileName);
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




	// 파일 삭제
	public void remove(long mediaId) {
		Media findmedia = findVerfiedMedia(mediaId);
		String fileName = (findmedia.getMediaUrl()).substring(68);

		// s3 버킷내에 해당 파일 있는지 먼저 확인
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





		// 이미지파일명 중복 방지, 파일명에 UUID를 붙여서 저장
		private String createFileName(String fileName) {
			return UUID.randomUUID().toString().concat(getFileExtension(fileName));
		}

		// 파일 유효성 검사
		private String getFileExtension(String fileName) {
			if (fileName.length() == 0) {
				throw new BusinessLogicException(ExceptionCode.WRONG_POSTING_MEDIA);
			}

			// 파일 유형 추가할 거 있으면 추가하자
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
	}
