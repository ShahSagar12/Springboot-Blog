package com.rest.treeleaf.blogpost.constants.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class CommentDto {
	@NonNull
	private String blogId;
	private String commentId;
	private String comment;
}
