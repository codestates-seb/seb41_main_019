package com.main19.server.posting.tags.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.posting.tags.entity.Tag;
import com.main19.server.posting.tags.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
	private final TagRepository tagRepository ;

	public Tag createTag(Tag tag) {
		// 있는 태그면
		if (findVerifiedTag(tag.getTagName())) {
			return findTag(tag.getTagName());
		}
		// 없는 태그이면 tag repository에 새로 저장
		return tagRepository.save(tag);

	}

	// public List<Tag> findTags() {
	// 	return tagRepository.findAll();
	// }

	// 있는 태그인지 확인
	public boolean findVerifiedTag(String tagName) {
		Optional<Tag> optionalTag = tagRepository.findByTagName(tagName);
		if (optionalTag.isPresent()) {
			return true;
		}
		return false;
	}

	// 없는 태그일 경우 tag
	public Tag findTag(String tagName) {
		Optional<Tag> optionalTag = tagRepository.findByTagName(tagName);
		Tag findTag =
			optionalTag.orElseThrow(() ->
				new BusinessLogicException(ExceptionCode.TAG_NOT_FOUND));
		return findTag;
	}
}
