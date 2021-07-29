package com.rest.treeleaf.blogpost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rest.treeleaf.blogpost.entity.BlogPost;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long>{
	
	@Modifying
	@Query(value="update blog_post set active=0 where blog_id=:blogId",nativeQuery = true)
	void deletePostByBlogId(@Param("blogId") String blogId);
	
	@Query(value="select * from blog_post where blog_id=:blogId",nativeQuery = true)
	BlogPost findByBlogId(@Param("blogId") String blogId);

	BlogPost findBlogPostByBlogId(String blogId);
	
	

}
