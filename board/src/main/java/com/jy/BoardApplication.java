package com.jy;

import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.jy.domain.board.Post;
import com.jy.repository.PostRepository;

@EnableJpaAuditing
@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}
	
//    @Bean
//    public CommandLineRunner runner(PostRepository postRepository) throws Exception {
//        return (args) -> {
//            IntStream.rangeClosed(1, 100).forEach(index ->
//                postRepository.save(Post.builder()
//                        .title("글" + index)
//                        .content("내용" + index).build()));
//        };
//    }

}
