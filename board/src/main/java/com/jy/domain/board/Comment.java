package com.jy.domain.board;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jy.domain.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table
public class Comment extends BaseTimeEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String content; // 댓글 내용
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;
	
	/* 댓글 수정 */
	public void update(String content){
		this.content = content;
	}
	
	/* 연관관계 주인에 값 입력 */
	public void addPost(Post post) {
		this.post = post;
	}
}
