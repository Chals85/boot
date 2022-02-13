package com.chals.boot.service;

import com.chals.boot.common.Status;
import com.chals.boot.dto.CommentDto;
import com.chals.boot.entity.Board;
import com.chals.boot.entity.Comment;
import com.chals.boot.exception.ForbiddenRequestException;
import com.chals.boot.exception.NotFoundBoardException;
import com.chals.boot.exception.NotFoundCommentException;
import com.chals.boot.repository.BoardRepository;
import com.chals.boot.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void saveComment(Long boardId,
                            CommentDto.SaveReqBody body) {
        // 게시글 조회
        Board board = boardRepository.findByIdAndStatus(boardId, Status.READ.getStatus())
                .orElseThrow(NotFoundBoardException::new);
        commentRepository.save(body.toEntity(board));
    }

    @Transactional
    public void updateComment(CommentDto.UpdateReqBody body) {
        // 댓글 조회
        Comment comment = commentRepository.findByIdAndStatus(body.getCommentId(), Status.READ.getStatus())
                .orElseThrow(NotFoundCommentException::new);
        // 댓글 수정
        comment.updateComment(body.getCommentContent());
    }

    @Transactional
    public void deleteComment(Long commentId,
                              String commentWriter) {
        // 댓글 조회
        Comment comment = commentRepository.findByIdAndStatus(commentId, Status.READ.getStatus())
                .orElseThrow(NotFoundCommentException::new);
        // 작성자 체크
        if(!comment.getCommentWriter().equals(commentWriter))
            throw new ForbiddenRequestException();
        // 댓글 삭제
        comment.deleteComment();
    }

}
