package com.rest.treeleaf.blogpost.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rest.treeleaf.blogpost.constants.dtos.PostDto;
import com.rest.treeleaf.blogpost.entity.BlogPost;
import com.rest.treeleaf.blogpost.repository.BlogPostRepository;
import com.rest.treeleaf.blogpost.service.BlogPostService;
import com.rest.treeleaf.common.models.ResponseModel;

@Service
public class BlogPostServiceImpl implements BlogPostService{

	private BlogPostRepository blogPostRepository;

	@Autowired
	public void setBlogPostRepository(BlogPostRepository blogPostRepository) {
		this.blogPostRepository = blogPostRepository;
	}

	@Override
	public ResponseEntity<?> addBlogPost(PostDto postDto) throws IllegalStateException, IOException {	
		BlogPost blogPost=getBlogPost(postDto);
		System.out.println("**********************************************************");
		System.out.println(blogPost);
//		BlogPost savedPost = blogPostRepository.save(blogPost);
//		return ResponseEntity.ok().body(new ResponseModel<Object, String>(savedPost, "Saved Successfully"));
		return null;
	}



	@Override
	public ResponseEntity<?> editBlogPost(PostDto postDto) {
		
		return null;
	}

	@Override
	public ResponseEntity<?> findAllBlogPosts() {
		
		return ResponseEntity.ok(new ResponseModel<Object, String>(blogPostRepository.findAll(), "All Blog Posts"));
	}

	@Override
	public ResponseEntity<?> findBlogPostsById(String blogId) {

		return ResponseEntity.ok(new ResponseModel<Object,String>(blogPostRepository.findByBlogId(blogId).get(), blogId));
	}

	@Override
	public ResponseEntity<?> deleteBlogPostById(String blogId) {
		blogPostRepository.deletePostByBlogId(blogId);
		return ResponseEntity.ok(new ResponseModel<String, String>(blogId, "Deleted Successfully"));
	}

	private BlogPost getBlogPost(PostDto postDto) throws IllegalStateException, IOException {
		BlogPost blogPost=new BlogPost();
		String path="D:\\JAVA\\Official\\Treeleaf_Workspace\\TreeleafBlog\\images\\"+System.currentTimeMillis()+".jpg";
		MultipartFile thumbnail=postDto.getThumbnail();
		thumbnail.transferTo(new File(path));
		blogPost.setImageUrl(path);
		blogPost.setTopics(postDto.getPostTopics());
		blogPost.setBody(postDto.getPostBody());
		return blogPost;
	}

}
