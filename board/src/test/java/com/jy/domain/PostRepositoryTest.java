package com.jy.domain;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jy.domain.board.Post;
import com.jy.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;


@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class PostRepositoryTest {
	
	@Autowired private PostRepository postRepository;
	
	private String title = "제목";
	private String content = "내용";
	
	@Test
	public void 도메인_테스트() {
		
		// given
		Post savedPost = postRepository.save(Post.builder()
								.title(title)
								.content(content)
								.build());
		// when
		Post post = postRepository.getById(savedPost.getId());
		// then
		assertThat(post.getTitle()).isEqualTo(title);
		assertThat(post.getContent()).isEqualTo(content);
		log.info("테스트 성공");
	}
}
