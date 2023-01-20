package com.main19.server.posting.service;

import com.main19.server.auth.jwt.JwtTokenizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.main19.server.member.entity.Member;
import com.main19.server.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.posting.entity.Media;
import com.main19.server.posting.entity.Posting;
import com.main19.server.posting.repository.MediaRepository;
import com.main19.server.posting.repository.PostingRepository;
import com.main19.server.utils.CustomBeanUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostingService {
	private final PostingRepository postingRepository;
	private final MediaRepository mediaRepository;
	private final MemberService memberService;
	private final CustomBeanUtils<Posting> beanUtils;
	private final JwtTokenizer jwtTokenizer;

	public Posting createPosting(Posting posting, long memberId ,List<String> mediaPaths) {

		Member findMember = memberService.findMember(memberId);
		posting.setMember(findMember);

		posting.setCreatedAt(LocalDateTime.now());

		for (String mediaUrl: mediaPaths) {
			Media media = new Media(mediaUrl, posting);
			mediaRepository.save(media);
			posting.getPostingMedias().add(media);
		}

		return postingRepository.save(posting);
	}

	public Posting updatePosting(Posting posting, String token) {

		if (posting.getMemberId() != jwtTokenizer.getMemberId(token)) {
			throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
		}

		Posting findPosting = findVerifiedPosting(posting.getPostingId());
		Posting updatePosting = beanUtils.copyNonNullProperties(posting, findPosting);

		updatePosting.setModifiedAt(LocalDateTime.now());

		return postingRepository.save(updatePosting);
	}

	public Posting findPosting(long postingId) {
		Posting findPosting = findVerifiedPosting(postingId);
		return findPosting;
	}

	public Page<Posting> findPostings(int page, int size) {
		// 최신순 이외에도 정렬 어떻게 할지 고려해야 함
		return postingRepository.findAll(PageRequest.of(page, size, Sort.by("postingId").descending()));
	}

	public Page<Posting> findPostingsByMemberId(long memberId, int page, int size) {
		return postingRepository.findByMember_MemberId(memberId, PageRequest.of(page, size, Sort.by("postingId").descending()));
	}

	public void deletePosting(long postingId) {
		Posting findPosting = findVerifiedPosting(postingId);
		postingRepository.delete(findPosting);
	}

	public Posting addMedia(long postingId, List<String> mediaPaths) {

		Posting findPosting = findVerifiedPosting(postingId);
		if (findPosting.getPostingMedias().size() + mediaPaths.size() > 3) {
			throw new BusinessLogicException(ExceptionCode.POSTING_MEDIA_ERROR);
		}

		for (String mediaUrl: mediaPaths) {
			Media media = new Media(mediaUrl, findPosting);
			mediaRepository.save(media);
			findPosting.getPostingMedias().add(media);
		}

		return findPosting;
	}

	public void deleteMedia(long mediaId) {
		Media findMedia = findVerfiedMedia(mediaId);
		mediaRepository.delete(findMedia);
	}

	public Posting findVerifiedPosting(long postingId) {
		Optional<Posting> optionalPosting = postingRepository.findById(postingId);
		Posting findPosting =
			optionalPosting.orElseThrow(() ->
				new BusinessLogicException(ExceptionCode.POSTING_NOT_FOUND));
		return findPosting;
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
