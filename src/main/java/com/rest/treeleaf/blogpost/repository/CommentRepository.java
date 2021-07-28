package com.rest.treeleaf.blogpost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.treeleaf.blogpost.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

}
