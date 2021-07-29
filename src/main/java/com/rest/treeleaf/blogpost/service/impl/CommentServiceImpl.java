package com.rest.treeleaf.blogpost.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.treeleaf.blogpost.constants.dtos.CommentDto;
import com.rest.treeleaf.blogpost.entity.Comment;
import com.rest.treeleaf.blogpost.repository.BlogPostRepository;
import com.rest.treeleaf.blogpost.repository.CommentRepository;
import com.rest.treeleaf.blogpost.service.CommentService;
import com.rest.treeleaf.common.models.ResponseModel;
import com.rest.treeleaf.common.provider.RandomIdProvider;
import com.rest.treeleaf.users.entity.User;
import com.rest.treeleaf.users.repository.UserRepository;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{
	
	private CommentRepository commentRepository;
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
	@Autowired
	public void setCommentRepository(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public ResponseEntity<?> addCommentOnBlog(CommentDto commentDto, String email)
			throws IllegalStateException, IOException {
		Comment savedComment=new Comment();
		Optional<User> user=userRepository.findByEmail(email);
		if(findSingleCommentsOnBlog(commentDto.getBlogId(), commentDto.getCommentId(),user.get().getId())==null) {
			Comment comment=objectMapper(commentDto);
			comment.setUserId(user.get().getId());
			savedComment=commentRepository.save(comment);
		}else {
			throw new RuntimeException("You have already commented on the post");
		}
		return ResponseEntity.ok().body(new ResponseModel<Object, String>(savedComment, "Saved Successfully"));
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public ResponseEntity<?> editCommentOnBlog(CommentDto commentDto, String email)
			throws IllegalStateException, IOException {
		Comment updateComment=new Comment();
		User user=userRepository.findByEmail(email).get();
		if(commentRepository.findIfExistingComment(commentDto.getComment(), user.getId(), commentDto.getBlogId())==null) {
			Comment existingComment=commentRepository.findCommentByCommentId(commentDto.getCommentId());
			Comment comment=objectMapper(commentDto);
			comment.setId(existingComment.getId());
			updateComment=commentRepository.save(comment);
			
		}else {
			throw new RuntimeException("You have already commented same comment in the blog");
		}
		return ResponseEntity.ok().body(new ResponseModel<Object, String>(updateComment, "Updated Successfully"));
	}

	@Override
	public ResponseEntity<?> findAllCommentsOnBlog(String blogId) {
		List<Comment> allCommentOnBlog = commentRepository.findAllCommentOnBlog(blogId);
		return ResponseEntity.ok(new ResponseModel<Object, String>(allCommentOnBlog, "All comments of Postid :"+blogId));
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public ResponseEntity<?> deleteCommentsOnBlog(String blogId,String commentId) {
		commentRepository.deleteCommentByBlogIdAndCommentId(blogId,commentId);
		return ResponseEntity.ok(new ResponseModel<String, String>(blogId+":"+commentId, "Deleted Successfullt"));
	}
	
	private Comment findSingleCommentsOnBlog(String blogId, String commentId,Long userId) {
		return commentRepository.findCommentByBlogIdAndCommentId(blogId,commentId,userId);
	}

	private Comment objectMapper(CommentDto commentDto) {
		Comment comment=new Comment();
		comment.setBlogId(commentDto.getBlogId());
		comment.setComment(commentDto.getComment());
		comment.setCommentId(RandomIdProvider.generateRandString(6));
		return comment;
	}

}
