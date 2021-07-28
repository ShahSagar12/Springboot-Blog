package com.rest.treeleaf.blogpost.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.rest.treeleaf.blogpost.constants.dtos.PostDto;

public interface BlogPostService {
	public ResponseEntity<?> addBlogPost(PostDto postDto) throws IllegalStateException, IOException; 
	public ResponseEntity<?> editBlogPost(PostDto postDto);
	public ResponseEntity<?> findAllBlogPosts();
	public ResponseEntity<?> findBlogPostsById(String blogId);
	public ResponseEntity<?> deleteBlogPostById(String blogId);

}
