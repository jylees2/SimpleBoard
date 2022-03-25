package com.jy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jy.domain.board.Comment;
import com.jy.domain.board.Post;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	/* 게시물 댓글 목록 가져오기 */
	List<Comment> getCommentByPostOrderById(Post post);
}
