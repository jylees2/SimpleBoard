package com.jy.service;

import java.util.List;

import com.jy.dto.CommentDto;

/* CRUD */
public interface CommentService {
	/* CREATE */
	Long save(Long id, CommentDto.RequestDto requestDTO);
	
	/* READ */
	List<CommentDto.ResponseDto> findAll(Long id);
	
	/* UPDATE */
	void update(Long comment_id, CommentDto.RequestDto requestDTO);
	
	/* DELETE */
	void delete(Long comment_id);
}
