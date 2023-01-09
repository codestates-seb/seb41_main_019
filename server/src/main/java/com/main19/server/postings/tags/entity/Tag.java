package com.main19.server.postings.tags.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.main19.server.postings.entity.Posting;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tagId;
	private String tagName;

	@OneToMany(mappedBy = "tag")
	private List<PostingTags> postings = new ArrayList<>();

}
