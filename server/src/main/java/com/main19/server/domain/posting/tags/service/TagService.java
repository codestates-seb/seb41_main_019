package com.main19.server.domain.posting.tags.service;

import java.util.Optional;

import com.main19.server.domain.posting.tags.repository.TagRepository;
import org.springframework.stereotype.Service;

import com.main19.server.global.exception.BusinessLogicException;
import com.main19.server.global.exception.ExceptionCode;
import com.main19.server.domain.posting.tags.entity.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
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
	@Transactional(readOnly = true)
	public boolean findVerifiedTag(String tagName) {
		Optional<Tag> optionalTag = tagRepository.findByTagName(tagName);
		if (optionalTag.isPresent()) {
			return true;
		}
		return false;
	}

	// 없는 태그일 경우 tag
	@Transactional(readOnly = true)
	public Tag findTag(String tagName) {
		Optional<Tag> optionalTag = tagRepository.findByTagName(tagName);
		Tag findTag =
			optionalTag.orElseThrow(() ->
				new BusinessLogicException(ExceptionCode.TAG_NOT_FOUND));
		return findTag;
	}
}
