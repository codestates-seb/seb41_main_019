package com.main19.server.domain.posting.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
public class PostingPatchDto {
	private String postingContent;
	@Size(max = 5)
	private List<@NotBlank @Length(min = 1, max = 15) String> tagName;
}
