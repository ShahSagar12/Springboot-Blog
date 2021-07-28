package com.rest.treeleaf.blogpost.constants.dtos;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class PostDto {
	private String postTopics;
	private String postBody;
	private MultipartFile thumbnail;
}
