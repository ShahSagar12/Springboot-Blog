package com.rest.treeleaf.blogpost.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rest.treeleaf.blogpost.constants.dtos.PostDto;
import com.rest.treeleaf.blogpost.entity.BlogPost;
import com.rest.treeleaf.blogpost.repository.BlogPostRepository;
import com.rest.treeleaf.blogpost.service.BlogPostService;
import com.rest.treeleaf.common.models.ResponseModel;
import com.rest.treeleaf.common.provider.RandomIdProvider;
import com.rest.treeleaf.users.repository.UserRepository;

@Service
@Transactional
public class BlogPostServiceImpl implements BlogPostService{

	private BlogPostRepository blogPostRepository;

	private UserRepository userRepository;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	@Autowired
	public void setBlogPostRepository(BlogPostRepository blogPostRepository) {
		this.blogPostRepository = blogPostRepository;
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public ResponseEntity<?> addBlogPost(PostDto postDto,String email) throws IllegalStateException, IOException {	
		BlogPost blogPost=getBlogPost(postDto,email);
		BlogPost savedPost = blogPostRepository.save(blogPost);
		return ResponseEntity.ok().body(new ResponseModel<Object, String>(savedPost, "Saved Successfully"));
	}



	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public ResponseEntity<?> editBlogPost(PostDto postDto,String email) throws IllegalStateException, IOException {
		BlogPost blogPost=getBlogPost(postDto,email);
		BlogPost existingPost = blogPostRepository.findByBlogId(postDto.getBlogId());
		if(existingPost.isActive()) {
			String imageUrl=blogPost.getImageUrl().isBlank()?existingPost.getImageUrl():blogPost.getImageUrl();
			blogPost.setImageUrl(imageUrl);
			blogPost.setPostId(existingPost.getPostId());
			blogPost=blogPostRepository.save(blogPost);
		}else {
			throw new RuntimeException("Blog is not active");
		}
		return ResponseEntity.ok().body(new ResponseModel<Object, String>(blogPost, "Updated Successfully"));
	}

	@Override
	public ResponseEntity<?> findAllBlogPosts() {
		List<BlogPost> allBlogPost = blogPostRepository.findAll();
		List<BlogPost> activeBlog = allBlogPost.parallelStream().filter(post->(post.isActive()==true)).collect(Collectors.toList());
		return ResponseEntity.ok(new ResponseModel<Object, String>(activeBlog, "All Blog Posts"));
	}

	@Override
	public ResponseEntity<?> findBlogPostsById(String blogId) {

		return ResponseEntity.ok(new ResponseModel<Object,String>(blogPostRepository.findByBlogId(blogId), blogId));
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public ResponseEntity<?> deleteBlogPostById(String blogId) {
		BlogPost blogPost = blogPostRepository.findByBlogId(blogId);
		if(blogPost.isActive()) {
			File file=new File(blogPost.getImageUrl());
			file.delete();
			blogPostRepository.deletePostByBlogId(blogId);
		}else {
			throw new RuntimeException("Blog is already deleted");
		}

		return ResponseEntity.ok(new ResponseModel<String, String>(blogId, "Deleted Successfully"));
	}

	private BlogPost getBlogPost(PostDto postDto,String email) throws IllegalStateException, IOException {
		BlogPost blogPost=new BlogPost();
		String path="D:\\JAVA\\Official\\Treeleaf_Workspace\\TreeleafBlog\\images\\"+System.currentTimeMillis()+".jpg";
		if(postDto.getThumbnail()!=null) {
			MultipartFile thumbnail=postDto.getThumbnail();
			thumbnail.transferTo(new File(path));
			blogPost.setImageUrl(path);
		}else {
			blogPost.setImageUrl("");
		}
		blogPost.setTopics(postDto.getPostTopics());
		blogPost.setBody(postDto.getPostBody());
		if(postDto.getBlogId().isEmpty()) {
			blogPost.setBlogId(postDto.getBlogId());
		}
		blogPost.setBlogId(RandomIdProvider.generateRandString(6));
		blogPost.setActive(true);
		blogPost.setUserId(userRepository.findByEmail(email).get().getId());
		return blogPost;
	}

}
