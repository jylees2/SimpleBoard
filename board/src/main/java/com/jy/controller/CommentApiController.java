package com.jy.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jy.dto.CommentDto;
import com.jy.service.CommentService;

import lombok.RequiredArgsConstructor;

/**
 * REST API CONTROLLER
 * @author juyoung
 *
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")

public class CommentApiController {
	private final CommentService commentService;
	
	/* CREATE */
	@PostMapping("/post/{id}/comment")
	public ResponseEntity<?> save(@PathVariable Long id, @RequestBody CommentDto.RequestDto requestDto) {
		commentService.save(id, requestDto);
		return ResponseEntity.ok("댓글 생성 완료");
	}
	
	/* READ */
	@GetMapping("/post/{id}/comment")
	public List<CommentDto.ResponseDto> read(@PathVariable Long id){
		return commentService.findAll(id);
	}
	
	/* UPDATE */
	@PutMapping("/post/{post_id}/comment/{comment_id}")
	public ResponseEntity<?> update(@PathVariable("comment_id") Long comment_id, @RequestBody CommentDto.RequestDto requestDto) {
		commentService.update(comment_id, requestDto);
		return ResponseEntity.ok("댓글 수정 완료");
	}
	
	/* DELETE */
	@DeleteMapping("/post/{post_id}/comment/{comment_id}")
	public ResponseEntity<?> delete(@PathVariable("comment_id") Long comment_id) {
		commentService.delete(comment_id);
		return ResponseEntity.ok("댓글 삭제 완료");
	}
}
