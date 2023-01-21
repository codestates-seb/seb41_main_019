package com.main19.server.posting.tags.service;

import java.util.List;

import com.main19.server.posting.repository.PostingRepository;
import org.springframework.stereotype.Service;

import com.main19.server.posting.entity.Posting;
import com.main19.server.posting.service.PostingService;
import com.main19.server.posting.tags.entity.PostingTags;
import com.main19.server.posting.tags.entity.Tag;
import com.main19.server.posting.tags.repository.PostingTagsRepository;

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
		postingTagsRepository.deletePostingTagsByPostingId(updatePosting.getPostingId());

		postingTags.setPosting(updatePosting);

		Tag tag	 = tagService.findTag(tagName);
		postingTags.setTag(tag);

		return postingTagsRepository.save(postingTags);
	}
}
