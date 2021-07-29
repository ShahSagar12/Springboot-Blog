package com.rest.treeleaf.blogpost.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.treeleaf.blogpost.constants.dtos.CommentDto;
import com.rest.treeleaf.blogpost.service.CommentService;

@RestController
@RequestMapping("/api/blog-post/comment")
public class CommentController {
	
	private CommentService commentService;

	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping
	ResponseEntity<?> addCommentOnBlogPost(OAuth2Authentication authentication,@Valid @RequestBody CommentDto commentDto) throws IllegalStateException, IOException{
		
		return commentService.addCommentOnBlog(commentDto, authentication.getName());
	}
	
	@GetMapping
	ResponseEntity<?> getCommentOnBlogPost(OAuth2Authentication authentication,@RequestParam("blogId") String blogId) throws IllegalStateException, IOException{
		
		return commentService.findAllCommentsOnBlog(blogId);
	}
	
	@PutMapping
	ResponseEntity<?> editCommentOnBlogPost(OAuth2Authentication authentication,@Valid @RequestBody CommentDto commentDto) throws IllegalStateException, IOException{
		
		return commentService.editCommentOnBlog(commentDto, authentication.getName());
	}
	
	@DeleteMapping
	ResponseEntity<?> deleteCommentOnBlogPost(OAuth2Authentication authentication,@RequestParam("blogId") String blogId,@RequestParam("commentId") String commentId) throws IllegalStateException, IOException{
		
		return commentService.deleteCommentsOnBlog(blogId,commentId);
	}
}
