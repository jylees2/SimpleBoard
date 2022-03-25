package com.jy.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.jy.dto.CommentDto;
import com.jy.dto.PostDto;
import com.jy.page.PageVo;
import com.jy.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postService;
	
	
	/* 리스트 화면 & 페이징 구현 */
	@GetMapping("list")
	public String list(Model model, 
			@RequestParam(required = false, defaultValue = "0", value="page") int pageNo,
			Pageable pageable) {
		
		// 클라이언트 페이지에서 받은 pageNo와 실제 접근 페이지는 다름. Page는 페이지가 0부터 시작
		// 따라서 실제 접근 페이지는 pageNo-1 처리 -> 컨트롤러에서 안 하고 싶은데..
		pageNo = (pageNo == 0) ? 0 : (pageNo - 1);
	
		Page<PostDto.ResponseDto> postList = postService.getPageList(pageable, pageNo); // page 객체 생성
		PageVo pageVo = postService.getPageInfo(postList, pageNo); // page 객체 관련 정보 가져옴
		
		model.addAttribute("postList", postList);
		model.addAttribute("pageNo", pageNo); // Model에 넘기는 pageNo은 실제 접근 페이지 번호, 따라서 view단에서는 pageNo+1 처리
		model.addAttribute("pageVo", pageVo);

		return "board/list";
	}
	
	/* 게시물 등록 폼 */
	@GetMapping("/post")
	public String create() {
		return "board/post-write";
	}
	
	/* 글 상세 보기 */
	@GetMapping("/post/read/{id}")
	public String read(@PathVariable Long id, Model model) {
		// 조회수 기능
		postService.updateView(id); // view++
		
		PostDto.ResponseDto responseDto = postService.findById(id);
		// 댓글 가져오기
		List<CommentDto.ResponseDto> comment = responseDto.getComment();
		
		/* 댓글 관련 */
		if(comment != null && !comment.isEmpty()) {
			model.addAttribute("comment", comment);
			
			log.info("comment 크기 : "+ comment.size());
		}

		model.addAttribute("post", responseDto);
		return "board/post-read";
	}
	
	/* 글 수정 */
	@GetMapping("/post/update/{id}")
	public String update(@PathVariable Long id, Model model) {
		PostDto.ResponseDto responseDto = postService.findById(id);
		model.addAttribute("post",responseDto);
		return "board/post-update";
	}
	
	/* 글 검색 */
	@GetMapping("/post/search")
	public String search(@RequestParam String keyword, Model model, 
			@RequestParam(required = false, defaultValue = "0", value="page") int pageNo,
			Pageable pageable) {
		
		pageNo = (pageNo == 0) ? 0 : (pageNo - 1);

		Page<PostDto.ResponseDto> searchList = postService.searchPageList(keyword, pageable, pageNo);
		PageVo pageVo = postService.getPageInfo(searchList, pageNo);
		
		model.addAttribute("searchList", searchList);
		model.addAttribute("keyword", keyword);
		model.addAttribute("pageVo", pageVo);

		return "board/post-search";
	}
	
}
