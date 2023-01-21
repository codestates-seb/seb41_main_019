package com.main19.server.storageService.s3;

import com.amazonaws.util.IOUtils;
import com.main19.server.storageService.ffmpeg.FileSystemStorageService;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

import javax.annotation.PostConstruct;

import com.main19.server.posting.entity.Posting;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MediaStorageService extends S3StorageService {
	private final AmazonS3 s3Client;
	private final MediaRepository mediaRepository;
	private final FileSystemStorageService fileSystemStorageService;

	public List<String> uploadMedia(List<MultipartFile> multipartFiles) {

		List<String> mediaUrlList = new ArrayList<>();

		for (MultipartFile file : multipartFiles) {
			if (file != null) {
				if (file.getContentType().contains("video")) {
					MultipartFile convertedFile = null;
					try {
						convertedFile = getMultipartFile(fileSystemStorageService.store(file));
						String fileName = createFileName(convertedFile.getOriginalFilename());
						ObjectMetadata objectMetadata = new ObjectMetadata();
						objectMetadata.setContentLength(convertedFile.getSize());
						objectMetadata.setContentType(convertedFile.getContentType());

						try (InputStream inputStream = convertedFile.getInputStream()) {
							s3Client.putObject(new PutObjectRequest(bucket + "/posting/media", fileName, inputStream, objectMetadata)
									.withCannedAcl(CannedAccessControlList.PublicRead));
							mediaUrlList.add(s3Client.getUrl(bucket + "/posting/media", fileName).toString());
							(fileSystemStorageService.store(file)).delete();
						} catch (IOException e) {
							throw new BusinessLogicException(ExceptionCode.MEDIA_UPLOAD_ERROR);
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				} else {
					String fileName = createFileName(file.getOriginalFilename());
					ObjectMetadata objectMetadata = new ObjectMetadata();
					objectMetadata.setContentLength(file.getSize());
					objectMetadata.setContentType(file.getContentType());

					try (InputStream inputStream = file.getInputStream()) {
						s3Client.putObject(new PutObjectRequest(bucket + "/posting/media", fileName, inputStream, objectMetadata)
								.withCannedAcl(CannedAccessControlList.PublicRead));
						mediaUrlList.add(s3Client.getUrl(bucket + "/posting/media", fileName).toString());
					} catch (IOException e) {
						throw new BusinessLogicException(ExceptionCode.MEDIA_UPLOAD_ERROR);
					}
				}
			}
		}
		return mediaUrlList;
	}


	public void removeAll(Posting posting) {
		List<Media> findMedias = posting.getPostingMedias();

		for (Media fileMedia : findMedias) {
			Media findMedia = findVerifiedMedia(fileMedia.getMediaId());
			String fileName = (fileMedia.getMediaUrl()).substring(68);

			if (!s3Client.doesObjectExist(bucket + "/posting/media", fileName)) {
				throw new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND);
			}
			s3Client.deleteObject(bucket + "/posting/media", fileName);
		}
	}

	public void remove(long mediaId) {
		Media findMedia = findVerifiedMedia(mediaId);
		String fileName = (findMedia.getMediaUrl()).substring(68);

		if (!s3Client.doesObjectExist(bucket + "/posting/media", fileName)) {
			throw new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND);
		}
			s3Client.deleteObject(bucket + "/posting/media", fileName);
		}

		private Media findVerifiedMedia(long mediaId) {
			Optional<Media> optionalMedia = mediaRepository.findById(mediaId);
			Media findMedia =
				optionalMedia.orElseThrow(() ->
					new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND));

			return findMedia;
		}

		@Override
		public String getFileExtension(String fileName) {
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
	private MultipartFile getMultipartFile(File file) throws IOException {
		FileItem fileItem = new DiskFileItem("originFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());

		try {
			InputStream input = new FileInputStream(file);
			OutputStream os = fileItem.getOutputStream();
			IOUtils.copy(input, os);
		} catch (IOException ex) {
			throw new BusinessLogicException(ExceptionCode.CONVERSION_FAILED);
		}
		MultipartFile mFile = new CommonsMultipartFile(fileItem);
		return mFile;
	}
}
