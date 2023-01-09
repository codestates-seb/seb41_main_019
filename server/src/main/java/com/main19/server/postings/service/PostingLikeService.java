package com.main19.server.postings.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.postings.entity.Posting;
import com.main19.server.postings.entity.PostingLike;
import com.main19.server.postings.repository.PostingLikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostingLikeService {
	private final PostingLikeRepository postingLikeRepository;
	private final PostingService postingService;

	public PostingLike createPostingLike(PostingLike postingLike, long postingId) {
		Posting posting = postingService.findVerifiedPosting(postingId);
		postingLike.setPosting(posting);
		return postingLikeRepository.save(postingLike);
	}

	public void deletePostingLike(long postingLikeId) {
		PostingLike findPostingLike = findVerifiedPostingLike(postingLikeId);

		postingLikeRepository.delete(findPostingLike);

	}

	private PostingLike findVerifiedPostingLike(long postingLikeId) {
		Optional<PostingLike> optionalPostingLike = postingLikeRepository.findById(postingLikeId);
		PostingLike findPostingLike =
			optionalPostingLike.orElseThrow(() -> new BusinessLogicException(ExceptionCode.POSTING_LIKE_NOT_FOUND));
		return findPostingLike;
	}
}
