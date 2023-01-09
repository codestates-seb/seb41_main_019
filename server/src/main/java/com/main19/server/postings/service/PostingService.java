package com.main19.server.postings.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.postings.entity.Media;
import com.main19.server.postings.entity.Posting;
import com.main19.server.postings.mapper.PostingMapper;
import com.main19.server.postings.repository.MediaRepository;
import com.main19.server.postings.repository.PostingLikeRepository;
import com.main19.server.postings.repository.PostingRepository;
import com.main19.server.postings.tags.entity.PostingTags;
import com.main19.server.postings.tags.repository.PostingTagsRepository;
import com.main19.server.postings.tags.repository.TagRepository;
import com.main19.server.utils.CustomBeanUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostingService {
	private final PostingRepository postingRepository;
	private final PostingLikeRepository postingLikeRepository;
	private final MediaRepository mediaRepository;
	private final PostingMapper mapper;
	private final CustomBeanUtils<Posting> beanUtils;

	public Posting createPosting(Posting posting, List<String> mediaPaths) {
		postingBlankCheck(mediaPaths);
		posting.setCreatedAt(LocalDateTime.now());

		for (String mediaUrl: mediaPaths) {
			Media media = new Media(mediaUrl,posting);
			mediaRepository.save(media);
		}
		return postingRepository.save(posting);
	}

	private void postingBlankCheck(List<String> mediaPaths) {
		if(mediaPaths == null || mediaPaths.isEmpty()) {
			throw new BusinessLogicException(ExceptionCode.POSTING_REQUIRES_AT_LEAST_ONE_MEDIA);
		}
	}

	public Posting updatePosting(Posting posting) {
		Posting findPosting = findVerifiedPosting(posting.getPostingId());
		Posting updatePosting = beanUtils.copyNonNullProperties(posting, findPosting);

		return postingRepository.save(updatePosting);
	}

	public Posting findPosting(Long postingId) {
		Posting findPosting = findVerifiedPosting(postingId);
		findPosting.setLikeCount(getLikeCount(postingId));

		return findPosting;
	}

	// 좋아요 카운트 같이 나오나?
	public Page<Posting> findPostings(int page, int size) {
		// 최신순 이외에도 정렬 어떻게 할지 고려해야 함
		return postingRepository.findAll(PageRequest.of(page, size, Sort.by("postingId").descending()));
	}

	public void deletePosting(long postingId) {
		Posting findPosting = findVerifiedPosting(postingId);

		postingRepository.delete(findPosting);
	}

	// 첨부파일 삭제
	public void deleteMedia(long mediaId) {
		Media findMedia = findVerfiedMedia(mediaId);

		mediaRepository.delete(findMedia);
	}

	public Posting findVerifiedPosting(Long postingId) {
		Optional<Posting> optionalPosting = postingRepository.findById(postingId);
		Posting findPosting =
			optionalPosting.orElseThrow(() ->
				new BusinessLogicException(ExceptionCode.POSTING_NOT_FOUND));
		return findPosting;
	}

	public Long getLikeCount(Long postingId) {
		Long likeCount = postingLikeRepository.countPostingLikesByPosting_PostingId(postingId);
		return likeCount;
	}

	// 첨부파일 삭제를 위해 추가
	public Media findVerfiedMedia(long mediaId) {
		Optional<Media> optionalMedia = mediaRepository.findById(mediaId);
		Media findMedia =
			optionalMedia.orElseThrow(() ->
				new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND));

		return findMedia;
	}
}
