package com.jy.dto;

import com.jy.domain.board.Comment;
import com.jy.domain.board.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * request, response DTO 클래스를 한 클래스로 InnerStaticClass로 한 번에 관리
 *
 */
public class CommentDto {

	/* 댓글 Service 요청을 위한 DTO 클래스 */
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Builder
	public static class RequestDto{
		
		private Long id;
		private String content;
		private String createdDate, modifiedDate;
		private Post post;
		
		/* DTO -> ENTITY */
		public Comment toEntity() {
			Comment comment = Comment.builder()
					.id(id)
					.content(content)
					.post(post)
					.build();
			
			return comment;
		}
	}
	
	/**
	 * 댓글 정보 리턴 Response 클래스
	 * Entity 클래스를 생성자 파라미터로 받아 데이터를 Dto로 변환하여 응답
	 * 별도의 전달 객체를 활용해 연관관게를 맺은 엔티티 간의 무한 참조 방지
	 */
	
	// 왜 선언?
	@Getter
	public static class ResponseDto{
		private Long id;
		private String content;
		private String createdDate, modifiedDate;
		private Long postId;
		
		/* ENTITY -> DTO */
		public ResponseDto(Comment comment) {
			this.id = comment.getId();
			this.content = comment.getContent();
			this.createdDate = comment.getCreatedDate();
			this.postId = comment.getPost().getId();
		}
	}
}
