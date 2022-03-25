package com.jy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.domain.board.Post;
import com.jy.dto.PostDto;
import com.jy.dto.PostDto.RequestDto;
import com.jy.dto.PostDto.ResponseDto;
import com.jy.page.PageVo;
import com.jy.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PostServiceImpl implements PostService{
	private final PostRepository postRepository;
	
	private static final int PAGE_POST_COUNT = 10; // 한 화면에 보일 컨텐츠 수를 static final 변수로 선언
	
	/* create */
	@Override
	public Long save(PostDto.RequestDto requestDto) {
		Post post = requestDto.toEntity();
		postRepository.save(post);
		log.info("저장 완료");
		return post.getId();
	}
	
	
	/* read */
	@Override
	@Transactional(readOnly = true)
	public PostDto.ResponseDto findById(Long id){
		Post post = postRepository.findById(id).orElseThrow(() ->
											new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
		
		log.info("id로 찾기 완료");
		return new PostDto.ResponseDto(post); // new 생성자로 응답 메시지로 감싸서 반환
	}


	/* update*/
	@Override
	public void update(Long id, RequestDto requestDto) {
		Post post = postRepository.findById(id).orElseThrow(() ->
											new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
		
		/* 수정 메서드 호출 */
		post.update(requestDto.getTitle(), requestDto.getContent());
		log.info("수정 완료");
	}


	/* delete */
	@Override
	public void delete(Long id) {
		Post post = postRepository.findById(id).orElseThrow(()->
											new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
		postRepository.delete(post);
		log.info("삭제 완료");
	}
		
	/* 리스트 페이징 */
	@Override
	@Transactional(readOnly = true)
	public Page<ResponseDto> getPageList(Pageable pageable, int pageNo) {
		pageable = PageRequest.of(pageNo, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "id"));
		Page<Post> page = postRepository.findAll(pageable);
		
		// 데이터를 그대로 노출하는 것은 위험, DTO로 변환해서 리턴할 것
        Page<PostDto.ResponseDto> map = page.map(post -> new PostDto.ResponseDto(post.getId(), post.getTitle(), null, post.getView(), post.getCreatedDate(), post.getModifiedDate(), null));
        return map;
	}

	/* 페이징 정보 반환 */
	@Override
	public PageVo getPageInfo(Page<ResponseDto> postList, int pageNo) {
		int totalPage = postList.getTotalPages();
		int startNumber = (int)((Math.floor(pageNo/PAGE_POST_COUNT)*PAGE_POST_COUNT)+1 <= totalPage ? (Math.floor(pageNo/PAGE_POST_COUNT)*PAGE_POST_COUNT)+1 : totalPage); // 현재 페이지를 통해 현재 페이지 그룹의 시작 페이지를 구함
		int endNumber = (startNumber + PAGE_POST_COUNT-1 < totalPage ? startNumber + PAGE_POST_COUNT-1 : totalPage); // 전체 페이지 수와 현재 페이지 그룹의 시작 페이지를 통해 현재 페이지 그룹의 마지막 페이지를 구함
		boolean hasPrev = postList.hasPrevious();
		boolean hasNext = postList.hasNext();
		
		return new PageVo(totalPage, startNumber, endNumber, hasPrev, hasNext);
	}


	/* 검색 페이징 */
	@Override
	@Transactional(readOnly = true)
	public Page<ResponseDto> searchPageList(String keyword, Pageable pageable, int pageNo) {
		pageable = PageRequest.of(pageNo, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "id"));
		Page<Post> page = postRepository.findByTitleContaining(keyword, pageable);
		
		log.info("totalElements:"+page.getTotalElements());
        Page<PostDto.ResponseDto> map = page.map(post -> new PostDto.ResponseDto(post.getId(), post.getTitle(), null, post.getView(), post.getCreatedDate(), post.getModifiedDate(), null));

		return map;
	}



	/* 조회수 처리 */
	@Override
	public int updateView(Long id) {
		postRepository.updateView(id);
		return 0;
	}
	
	
}
