package com.main19.server.domain.posting.tags.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tagId;
	private String tagName;

	@OneToMany(mappedBy = "tag", cascade = CascadeType.PERSIST)
	private List<PostingTags> postings = new ArrayList<>();

}
