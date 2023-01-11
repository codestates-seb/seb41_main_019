package com.main19.server.postings.tags.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.main19.server.postings.entity.Posting;
import com.main19.server.postings.repository.PostingRepository;
import com.main19.server.postings.service.PostingService;
import com.main19.server.postings.tags.entity.PostingTags;
import com.main19.server.postings.tags.entity.Tag;
import com.main19.server.postings.tags.repository.PostingTagsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostingTagsService {
	private final PostingTagsRepository postingTagsRepository;
	private final PostingService postingService;
	private final TagService tagService;

	public PostingTags createPostingTags(PostingTags postingTags, long postingId, String tagName) {
		Posting posting = postingService.findVerifiedPosting(postingId);
		postingTags.setPosting(posting);
		Tag tag	 = tagService.findTag(tagName);
		postingTags.setTag(tag);

		// for (int i = 0; i < tagName.size(); i++){
		// 	Tag tag	 = tagService.findTag(tagName.get(i));
		// 	postingTags.setTag(tag);
		// }

		return postingTagsRepository.save(postingTags);
	}

	public PostingTags updatePostingTags(PostingTags postingTags, long postingId, String tagName) {
		List<PostingTags> getpostingTags = findPostingTagsByPostingId(postingId);
		// 수정 시 기존에 있던거 싹 삭제하고 가져오도록 for문 돌렸는데 더 좋은 방법이 있을까요..
		for(int i = 0; i < getpostingTags.size(); i++) {
			postingTagsRepository.delete(getpostingTags.get(i));
		}

		Posting posting = postingService.findVerifiedPosting(postingId);
		postingTags.setPosting(posting);

		Tag tag	 = tagService.findTag(tagName);
		postingTags.setTag(tag);

		return postingTagsRepository.save(postingTags);
	}

	private List<PostingTags> findPostingTagsByPostingId(long postingId) {
		List<PostingTags> getpostingTags = postingTagsRepository.findAllByPosting_PostingId(postingId);
		return getpostingTags;
	}
}
