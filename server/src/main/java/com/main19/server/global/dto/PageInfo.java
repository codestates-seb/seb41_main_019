package com.main19.server.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PageInfo {
	private int page;
	private int size;
	private long totalElements;
	private int totalPages;
}
