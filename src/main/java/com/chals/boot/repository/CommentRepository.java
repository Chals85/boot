package com.chals.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chals.boot.entity.Comment;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 댓글 조회
     * @param commentId 댓글 아이디
     * @param status 상대값
     * @return 댓글
     */
    Optional<Comment> findByIdAndStatus(Long commentId,
                                        String status);

    /**
     * 게시물 등록된 댓글 목록 조회
     * @param boardId 게시물 아이디
     * @return 목록
     */
    List<Comment> findByBoard_Id(Long boardId);

}
