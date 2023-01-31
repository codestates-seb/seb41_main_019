package com.main19.server.posting.like.service;

import com.main19.server.auth.jwt.JwtTokenizer;
import com.main19.server.sse.entity.Sse.SseType;
import com.main19.server.sse.service.SseService;
import java.util.Optional;

import com.main19.server.posting.service.PostingService;
import org.springframework.stereotype.Service;

import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.member.entity.Member;
import com.main19.server.member.service.MemberService;
import com.main19.server.posting.entity.Posting;
import com.main19.server.posting.like.entity.PostingLike;
import com.main19.server.posting.like.repository.PostingLikeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostingLikeService {
	private final PostingLikeRepository postingLikeRepository;
	private final PostingService postingService;
	private final MemberService memberService;
	private final JwtTokenizer jwtTokenizer;
	private final SseService sseService;

	public PostingLike createPostingLike(long postingId, long memberId, String token) {

		if (memberId != jwtTokenizer.getMemberId(token)) {
			throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
		}

		PostingLike findPostingLike = postingLikeRepository.findByMember_MemberIdAndPosting_PostingId(memberId,postingId);

		if(findPostingLike != null) {
			throw new BusinessLogicException(ExceptionCode.POSTING_LIKE_ERROR);
		}

		Posting posting = postingService.findVerifiedPosting(postingId);
		Member member = memberService.findMember(memberId);
		PostingLike postingLike= new PostingLike();

		postingLike.setPosting(posting);
		postingLike.setMember(member);

		posting.createLikeCount();

		if(posting.getMemberId() != jwtTokenizer.getMemberId(token)) {
			sseService.sendPosting(posting.getMember(), SseType.postLike, member, posting);
		}

		return postingLikeRepository.save(postingLike);
	}

	public void deletePostingLike(long postingLikeId, String token) {

		if (findVerifiedPostingLike(postingLikeId).getMemberId() != jwtTokenizer.getMemberId(token)) {
			throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
		}

		PostingLike findPostingLike = findVerifiedPostingLike(postingLikeId);

		if(findPostingLike.getPostingLikeCount() == 0) {
			throw new BusinessLogicException(ExceptionCode.POSTING_LIKE_NOT_FOUND);
		}

		findPostingLike.getPosting().deleteLikeCount();

		postingLikeRepository.delete(findPostingLike);

	}

	private PostingLike findVerifiedPostingLike(long postingLikeId) {
		Optional<PostingLike> optionalPostingLike = postingLikeRepository.findById(postingLikeId);
		PostingLike findPostingLike =
			optionalPostingLike.orElseThrow(() -> new BusinessLogicException(ExceptionCode.POSTING_LIKE_NOT_FOUND));
		return findPostingLike;
	}
}
