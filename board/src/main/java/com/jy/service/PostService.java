package com.jy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jy.dto.PostDto;
import com.jy.page.PageVo;

public interface PostService {

	/* 페이징 관련 */
	Page<PostDto.ResponseDto> getPageList(Pageable pageable, int pageNo);
	
	/* 페이징 관련 파라미터 */
	PageVo getPageInfo(Page<PostDto.ResponseDto> postList, int pageNo);
	
	/* create */
	Long save(PostDto.RequestDto dto);
	/* read */
	PostDto.ResponseDto findById(Long id);
	/* update */
	void update(Long id, PostDto.RequestDto requestDto);
	/* delete */
	void delete(Long id);
	
	/* search */
	Page<PostDto.ResponseDto> searchPageList(String keyword, Pageable pageable, int pageNo);
	
	/* view counting */
	int updateView(Long id);
}
