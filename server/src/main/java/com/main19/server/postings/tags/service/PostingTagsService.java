package com.main19.server.postings.tags.service;

import com.main19.server.postings.tags.entity.PostingTags;
import com.main19.server.postings.tags.repository.PostingTagsRepository;

import org.springframework.stereotype.Service;

import com.main19.server.postings.entity.Posting;
import com.main19.server.postings.service.PostingService;
import com.main19.server.postings.tags.entity.Tag;

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
}
