package com.main19.server.domain.posting.tags.service;

import com.main19.server.domain.posting.tags.repository.PostingTagsRepository;
import org.springframework.stereotype.Service;

import com.main19.server.domain.posting.entity.Posting;
import com.main19.server.domain.posting.tags.entity.PostingTags;
import com.main19.server.domain.posting.tags.entity.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostingTagsService {
	private final PostingTagsRepository postingTagsRepository;
	private final TagService tagService;

	public PostingTags createPostingTags(PostingTags postingTags, Posting posting, String tagName) {
		postingTags.setPosting(posting);
		Tag tag	 = tagService.findTag(tagName);
		postingTags.setTag(tag);

		return postingTagsRepository.save(postingTags);
	}

	public PostingTags updatePostingTags(PostingTags postingTags, Posting updatePosting, String tagName) {

		postingTags.setPosting(updatePosting);

		Tag tag	 = tagService.findTag(tagName);
		postingTags.setTag(tag);

		return postingTagsRepository.save(postingTags);
	}
}
