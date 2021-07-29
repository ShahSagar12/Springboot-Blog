package com.rest.treeleaf.blogpost.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import com.rest.treeleaf.blogpost.constants.dtos.CommentDto;

public interface CommentService {
	
	public ResponseEntity<?> addCommentOnBlog(CommentDto commentDto,String email) throws IllegalStateException, IOException; 
	public ResponseEntity<?> editCommentOnBlog(CommentDto commentDto,String email) throws IllegalStateException, IOException;
	public ResponseEntity<?> findAllCommentsOnBlog(String blogId);
	public ResponseEntity<?> deleteCommentsOnBlog(String blogId,String commentId);

}
