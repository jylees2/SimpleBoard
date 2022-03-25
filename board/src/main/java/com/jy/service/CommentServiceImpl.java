package com.jy.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.domain.board.Comment;
import com.jy.domain.board.Post;
import com.jy.dto.CommentDto;
import com.jy.repository.CommentRepository;
import com.jy.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentServiceImpl implements CommentService{

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	
	/* CREATE */
	@Override
	public Long save(Long id, CommentDto.RequestDto requestDto) {
		
		// 댓글을 작성한 게시글 찾기
		Post post = postRepository.findById(id).orElseThrow(() ->
						new IllegalArgumentException("댓글 쓰기 실패 : 해당 게시글이 존재하지 않습니다. " + id));
		
		// DTO -> ENTITY
		Comment comment = requestDto.toEntity();
		comment.addPost(post); // comment 엔티티에 post 값 추가
		commentRepository.save(comment);
		return comment.getId();
	}

	/* READ */
	@Transactional(readOnly = true)
	@Override
	public List<CommentDto.ResponseDto> findAll(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() ->
						new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id :"+id));

		// post의 id로 찾은 댓글을 List 컬렉션으로 가져오기
		List<Comment> comment = post.getComment();
		return comment.stream().map(CommentDto.ResponseDto::new).collect(Collectors.toList());
	}

	/* UPDATE */
	@Override
	public void update(Long comment_id, CommentDto.RequestDto requestDto) {
		Comment comment = commentRepository.findById(comment_id).orElseThrow(() ->
							new IllegalArgumentException("해당 댓글이 존재하지 않습니다. "+ comment_id));
		
		// comment 도메인 클래스의 update 메서드 이용
		comment.update(requestDto.getContent());
		
	}

	/* DELETE */
	@Override
	public void delete(Long comment_id) {
		Comment comment = commentRepository.findById(comment_id).orElseThrow(() ->
							new IllegalArgumentException("해당 댓글이 존재하지 않습니다. "+comment_id));
		commentRepository.delete(comment);
	}
	

}
