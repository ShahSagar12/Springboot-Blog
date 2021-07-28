package com.rest.treeleaf.blogpost.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.rest.treeleaf.common.models.AuditModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
	private boolean active;
	
	public Comment(String comment, Long userId, String commentId, boolean active) {
		this.comment = comment;
		this.userId = userId;
		this.commentId = commentId;
		this.active = active;
	}
}
