package com.jy.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jy.domain.board.Comment;
import com.jy.domain.board.Post;
import com.jy.repository.CommentRepository;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Slf4j
public class CommentRepositoryTest {
	@Autowired
	private CommentRepository commentRepository;
	
	@Test
	public void 댓글_생성_조회() {
		
		// given
		String content = "댓글";
		
		Post post = Post.builder()
				.title("title")
				.content("content")
				.build();
		
		// when
		commentRepository.save(Comment.builder()
							.content(content)
							.post(post)
							.build());
		List<Comment> commentList = commentRepository.getCommentByPostOrderById(post);
		
		// then
		Comment comment = commentList.get(0);
		assertThat(comment.getContent()).isEqualTo(content);
		
		log.info("테스트 완료");
	}
}
