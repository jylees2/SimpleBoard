package com.jy.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.jy.domain.board.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * request, response dto inner class로 한 번에 관리
 *
 */
public class PostDto {

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class RequestDto{
		private Long id;
		private String title;
		private String content;
		private int view;
		private String createdDate, modifiedDate;
		
		public Post toEntity() {
			Post post = Post.builder()
							.id(id)
							.title(title)
							.content(content)
							.view(0)
							.build();
			
			return post;
		}
	}
	
	/**
	 * 게시글 정보를 리턴할 응답 클래스
	 * Entity 클래스를 생성자 파라미터로 받아 데이터를 DTO로 변환하여 응답
	 * 별도의 전달 객체를 활용해 연관관계를 맺은 엔티티간의 무한 참조를 방지
	 */
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ResponseDto{
		private Long id;
		private String title;
		private String content;
		private int view;
		private String createdDate, modifiedDate;
		private List<CommentDto.ResponseDto> comment; // 댓글
		
		// Entity -> Dto
		public ResponseDto(Post post) {
			this.id = post.getId();
			this.title = post.getTitle();
			this.content = post.getContent();
			this.view = post.getView();
			this.createdDate = post.getCreatedDate();
			this.modifiedDate = post.getModifiedDate();
			// Comment 필드의 List 타입을 DTO 클래스로 하여 연관관계를 맺은 엔티티 간 무한 참조를 방지
			this.comment = post.getComment().stream().map(CommentDto.ResponseDto::new).collect(Collectors.toList());
		}
		
	}
}
