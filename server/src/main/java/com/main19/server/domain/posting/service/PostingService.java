package com.main19.server.domain.posting.service;

import com.main19.server.global.auth.jwt.JwtTokenizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.main19.server.domain.member.entity.Member;
import com.main19.server.domain.member.service.MemberService;
import com.main19.server.domain.posting.dto.PostingPostDto;
import com.main19.server.domain.posting.entity.Media;
import com.main19.server.domain.posting.mapper.PostingMapper;
import com.main19.server.domain.posting.repository.MediaRepository;
import com.main19.server.domain.posting.repository.PostingRepository;
import com.main19.server.domain.posting.dto.PostingPatchDto;
import com.main19.server.domain.posting.tags.entity.PostingTags;
import com.main19.server.domain.posting.tags.repository.PostingTagsRepository;
import com.main19.server.domain.posting.tags.service.PostingTagsService;
import com.main19.server.domain.posting.tags.service.TagService;
import com.main19.server.global.storageService.s3.MediaStorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.main19.server.global.exception.BusinessLogicException;
import com.main19.server.global.exception.ExceptionCode;
import com.main19.server.domain.posting.entity.Posting;
import com.main19.server.global.utils.CustomBeanUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class PostingService {
	private final PostingRepository postingRepository;
	private final MediaRepository mediaRepository;
	private final MemberService memberService;
	private final TagService tagService;
	private final PostingTagsService postingTagsService;
	private final MediaStorageService storageService;
	private final PostingMapper mapper;
	private final CustomBeanUtils<Posting> beanUtils;
	private final JwtTokenizer jwtTokenizer;
	private final PostingTagsRepository postingTagsRepository;

	public Posting createPosting(PostingPostDto requestBody, long memberId, List<MultipartFile> multipartFiles, String token) {

		if (memberId != jwtTokenizer.getMemberId(token)) {
			throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
		}

		Posting posting = mapper.postingPostDtoToPosting(requestBody);

		Member findMember = memberService.findMember(memberId);
		posting.setMember(findMember);

		posting.setCreatedAt(LocalDateTime.now());

		List<Media> mediaList = storageService.uploadMedia(multipartFiles, posting);
		posting.setPostingMedias(mediaList);


		createPostingTags(requestBody, posting);

		return postingRepository.save(posting);
	}

	public Posting updatePosting(long postingId, PostingPatchDto requestBody, String token) {

		Posting posting = mapper.postingPatchDtoToPosting(requestBody);
		posting.setPostingId(postingId);
		Posting findPosting = findVerifiedPosting(postingId);

		if (findPosting.getMemberId() != jwtTokenizer.getMemberId(token)) {
			throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
		}

		Posting updatePosting = beanUtils.copyNonNullProperties(posting, findPosting);

		updatePosting.setModifiedAt(LocalDateTime.now());

		if(requestBody.getTagName() != null) updatePostingTags(requestBody, updatePosting);

		return postingRepository.save(updatePosting);
	}

	@Transactional(readOnly = true)
	public Posting findPosting(long postingId) {
		Posting findPosting = findVerifiedPosting(postingId);
		return findPosting;
	}

	@Transactional(readOnly = true)
	public Page<Posting> findPostings(int page, int size) {
		return postingRepository.findAll(PageRequest.of(page, size, Sort.by("postingId").descending()));
	}

	@Transactional(readOnly = true)
	public Page<Posting> findPostingsByFollowing(int page, int size, String token) {
		Member member = memberService.findMember(jwtTokenizer.getMemberId(token));
		return  postingRepository.findByMember_FollowingList(member.getMemberId(), PageRequest.of(page, size, Sort.by("posting_id").descending()));
	}

	@Transactional(readOnly = true)
	public Page<Posting> findPostingsByMemberId(long memberId, int page, int size) {
		return postingRepository.findByMember_MemberId(memberId, PageRequest.of(page, size, Sort.by("postingId").descending()));
	}

	@Transactional(readOnly = true)
	public Page<Posting> sortPostingsByLikes(int page, int size) {
		return postingRepository.findAll(PageRequest.of(page, size, Sort.by("likeCount").descending()));
	}

	@Transactional(readOnly = true)
	public Page<Posting> sortFollowPostingsByLikes(int page, int size, String token) {
		Member member = memberService.findMember(jwtTokenizer.getMemberId(token));
		return postingRepository.findByMember_FollowingList(member.getMemberId(), PageRequest.of(page, size, Sort.by("like_count").descending()));
	}

	@Transactional(readOnly = true)
	public Page<Posting> findPostingsByStrContent(int page, int size, String str) {
		str = "%" + str + "%";
		Page<Posting> postingListSearchByPostingContent =
				postingRepository.findPostingsByPostingContent(str, PageRequest.of(page, size, Sort.by("posting_id").descending()));

		return postingListSearchByPostingContent;
	}

	public Page<Posting> findPostingsByStrTag(int page, int size, String str) {
		str = "%" + str + "%";
		Page<Posting> postingListSearchByTagName =
				postingRepository.findPostingsByTagName(str, PageRequest.of(page, size, Sort.by("posting_id").descending()));

		return postingListSearchByTagName;
	}


	public void deletePosting(long postingId, String token) {
		Posting findPosting = findVerifiedPosting(postingId);

		if (findPosting.getMember().getMemberId() != jwtTokenizer.getMemberId(token)) {
			throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
		}

		storageService.removeAll(findPosting);
		postingRepository.delete(findPosting);
	}

	public Posting addMedia(long postingId, List<MultipartFile> multipartFiles, String token) {
		Posting findPosting = findVerifiedPosting(postingId);

		if (findPosting.getMember().getMemberId() != jwtTokenizer.getMemberId(token)) {
			throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
		}

		countMedias(findPosting, multipartFiles);
		List<Media> mediaList = storageService.uploadMedia(multipartFiles, findPosting);
		findPosting.setPostingMedias(mediaList);

		return findPosting;
	}

	public void deleteMedia(long mediaId, String token) {
		Posting posting = findVerifiedMedia(mediaId).getPosting();

		if (posting.getMember().getMemberId() != jwtTokenizer.getMemberId(token)) {
			throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
		}

		storageService.remove(mediaId);

		Media findMedia = findVerifiedMedia(mediaId);
		mediaRepository.delete(findMedia);
	}

	@Transactional(readOnly = true)
	public Posting findVerifiedPosting(long postingId) {
		Optional<Posting> optionalPosting = postingRepository.findById(postingId);
		Posting findPosting =
			optionalPosting.orElseThrow(() ->
				new BusinessLogicException(ExceptionCode.POSTING_NOT_FOUND));
		return findPosting;
	}

	@Transactional(readOnly = true)
	public Media findVerifiedMedia(long mediaId) {
		Optional<Media> optionalMedia = mediaRepository.findById(mediaId);
		Media findMedia =
			optionalMedia.orElseThrow(() ->
				new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND));
		return findMedia;
	}

	private void countMedias(Posting findPosting, List<MultipartFile> multipartFiles) {
		int cntMultipartFiles = 0;
		if (multipartFiles.get(1) == null) {
			cntMultipartFiles = 2;
			if (multipartFiles.get(2) == null) {
				cntMultipartFiles = 1;
			}
		} else cntMultipartFiles = 3;

		if (findPosting.getPostingMedias().size() + cntMultipartFiles > 3) {
			throw new BusinessLogicException(ExceptionCode.POSTING_MEDIA_ERROR);
		}
	}

	private void createPostingTags(PostingPostDto requestBody, Posting posting) {
		for (String tagName : requestBody.getTagName()) {
			tagService.createTag(mapper.tagPostDtoToTag(tagName));
			PostingTags postingTags = mapper.postingPostDtoToPostingTag(requestBody);
			postingTagsService.createPostingTags(postingTags, posting, tagName);
		}
	}

	private void updatePostingTags(PostingPatchDto requestBody, Posting updatePosting) {

		postingTagsRepository.deletePostingTagsByPostingId(updatePosting.getPostingId());

		for (String tagName : requestBody.getTagName()) {
			tagService.createTag(mapper.tagPostDtoToTag(tagName));
			PostingTags postingTags = mapper.postingPatchDtoToPostingTag(requestBody);
			postingTagsService.updatePostingTags(postingTags, updatePosting, tagName);
		}
	}
}
