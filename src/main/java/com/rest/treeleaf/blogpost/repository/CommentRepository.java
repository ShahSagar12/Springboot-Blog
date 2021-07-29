package com.rest.treeleaf.blogpost.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.rest.treeleaf.blogpost.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	@Query(value="select * from comment where comment_id=:commentId and blog_id=:blogId and user_id=:userId",nativeQuery = true)
	Comment findCommentByBlogIdAndCommentId(@Param("blogId") String blogId,@Param("commentId") String commentId,@Param("userId") Long userId);
	
	@Query(value="select * from comment where blog_id=:blogId",nativeQuery = true)
	List<Comment> findAllCommentOnBlog(@Param("blogId") String blogId);
	
	@Modifying
	@Query(value="delete from comment where blog_id=:blogId and comment_id=:commentId",nativeQuery = true)
	void deleteCommentByBlogIdAndCommentId(@Param("blogId") String blogId,@Param("commentId") String commentId);
	
	@Query(value="select * from comment where comment=:comment and user_id=:userId and blog_id=:blogId",nativeQuery = true)
	Comment findIfExistingComment(@Param("comment") String comment,@Param("userId") Long userId,@Param("blogId") String blogId);

	Comment findCommentByCommentId(String commentId);

}
