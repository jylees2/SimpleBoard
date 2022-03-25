package com.jy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jy.dto.PostDto;
import com.jy.service.PostService;
import lombok.RequiredArgsConstructor;

/**
 * Rest API Controller
 *
 */

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostApiController {
	
	private final PostService postService;

	/* 게시물 등록 */
	@PostMapping("/post")
	public ResponseEntity<?> create(@RequestBody PostDto.RequestDto requestDto){
		postService.save(requestDto);
		return new ResponseEntity<>("{}", HttpStatus.CREATED);
	}
	
	/* 게시물 상세 */
	@GetMapping("/post/{id}")
	public ResponseEntity<?> read(@PathVariable Long id){
		return ResponseEntity.ok(id);
	}
	
	/* 게시물 수정 */
	@PutMapping("/post/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PostDto.RequestDto requestDto){
		postService.update(id, requestDto);
		return ResponseEntity.ok(id);
	}
	
	/* 게시물 삭제 */
	@DeleteMapping("/post/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		postService.delete(id);
		return ResponseEntity.ok(id);
	}
}
