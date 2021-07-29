package com.rest.treeleaf.blogpost.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.rest.treeleaf.common.models.AuditModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Comment extends AuditModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String comment;
	private Long userId;
	private String commentId;
	private String blogId;
	
	public Comment(String comment, Long userId, String commentId, String blogId) {
		this.comment = comment;
		this.userId = userId;
		this.commentId = commentId;
		this.blogId = blogId;
	}
}
