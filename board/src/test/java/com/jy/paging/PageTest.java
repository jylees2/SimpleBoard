package com.jy.paging;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.jy.domain.board.Post;
import com.jy.dto.PostDto;
import com.jy.page.PageVo;
import com.jy.repository.PostRepository;
import com.jy.service.PostService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PageTest {
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private PostService postService;

	@Test
	public void 페이징_테스트() {
		// given
		for(int i=0; i<10; i++) {
			postRepository.save(Post.builder()
					.title("제목"+i)
					.content("내용"+i)
					.build());
		}
		postRepository.save(Post.builder()
					.title("안녕")
					.content("하이")
					.build());
		
		// when
		Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "id");
		Page<PostDto.ResponseDto> postList = postService.getPageList(pageable, 0);// page 객체 생성
		PageVo pageVo = postService.getPageInfo(postList, 0); // page 객체 관련 정보 가져옴
		
		Page<PostDto.ResponseDto> searchList = postService.searchPageList("안녕", pageable, 0);
		PageVo searchVo = postService.getPageInfo(searchList, 0);
		// then
		assertThat(postList.getTotalElements()).isEqualTo(11);
		assertThat(pageVo.totalPage).isEqualTo(2);
		assertThat(pageVo.startNumber).isEqualTo(1);
		assertThat(pageVo.endNumber).isEqualTo(3);
		
		assertThat(searchList.getTotalElements()).isEqualTo(1);
		assertThat(searchVo.totalPage).isEqualTo(1);
		assertThat(searchVo.startNumber).isEqualTo(1);
		assertThat(searchVo.endNumber).isEqualTo(1);
		
		log.info("페이징 테스트 성공");
	}
}
