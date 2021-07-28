package com.rest.treeleaf.blogpost.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rest.treeleaf.blogpost.constants.dtos.PostDto;
import com.rest.treeleaf.blogpost.service.BlogPostService;

@RestController
@RequestMapping("/api/blog-post")
public class BlogPostController {
	
	private BlogPostService blogPostService;
	
	@Autowired
	public void setBlogPostService(BlogPostService blogPostService) {
		this.blogPostService = blogPostService;
	}
	
	@PostMapping(consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> addPost(OAuth2Authentication oauth2Auth2Authentication,
									@RequestParam("postTopics") String postTopics,
									@RequestParam("postBody") String postBody,
									@RequestPart("thumbnail") MultipartFile multipartFile) throws IllegalStateException, IOException{
		PostDto postDto = new PostDto(postTopics, postBody, multipartFile);
		return blogPostService.addBlogPost(postDto);	
	}
}
