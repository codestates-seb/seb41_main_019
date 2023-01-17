package com.main19.server.posting.tags.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

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

	@OneToMany(mappedBy = "tag", cascade = CascadeType.PERSIST)
	private List<PostingTags> postings = new ArrayList<>();

}
