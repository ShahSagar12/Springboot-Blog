package com.rest.treeleaf.blogpost.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.rest.treeleaf.common.models.AuditModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class BlogPost extends AuditModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;
	
	private String topics;
	private String body;
	private Long userId;
	private String blogId;
	private String imageUrl;
	private boolean active;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Comment> comments=new ArrayList<>();

	public BlogPost(String topics, String body, Long userId, String blogId, String imageUrl, boolean active,
			List<Comment> comments) {
		this.topics = topics;
		this.body = body;
		this.userId = userId;
		this.blogId = blogId;
		this.imageUrl = imageUrl;
		this.active = active;
		this.comments = comments;
	}
	
}
