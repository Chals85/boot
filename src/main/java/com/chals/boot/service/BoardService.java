package com.chals.boot.service;

import com.chals.boot.common.Status;
import com.chals.boot.entity.Board;
import com.chals.boot.exception.ForbiddenRequestException;
import com.chals.boot.exception.NotFoundBoardException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chals.boot.dto.BoardDto;
import com.chals.boot.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	
	public void saveBoard(BoardDto.SaveReqBody body) {
		boardRepository.save(body.toEntity());
	}

	@Transactional(readOnly = true)
	public BoardDto.FindAllResBody findAllBoard(String searchKey,
												String searchVal,
												Pageable pageable) {
		return boardRepository.findAll(searchKey,
									   searchVal,
									   pageable);
	}

	@Transactional
	public BoardDto.FindResBody findBoard(Long boardId) {
		Board board = boardRepository.findByIdAndStatus(boardId, Status.READ.getStatus())
				.orElseThrow(NotFoundBoardException::new);
		// 방문자 읽기 추가
		board.visitBoardRead();

		return BoardDto.FindResBody.builder()
				.boardId(board.getId())
				.boardWriter(board.getBoardWriter())
				.boardTitle(board.getBoardTitle())
				.boardContent(board.getBoardContent())
				.readCount(board.getReadCount())
				.createAt(board.getCreateAt())
				.updateAt(board.getUpdateAt())
				// 댓글목록 추가
				.comments(
						board.getBoardComments().stream()
								.filter(comment -> comment.getStatus().equals(Status.READ.getStatus()))
								.map(
										comment -> BoardDto.Comment.builder()
												.commentId(comment.getId())
												.commentWriter(comment.getCommentWriter())
												.commentContent(comment.getCommentContent())
												.createAt(comment.getCreateAt())
												.updateAt(comment.getUpdateAt())
												.build()
								).collect(Collectors.toList())
				)
				.build();
	}

	@Transactional
	public void updateBoard(BoardDto.UpdateReqBody body) {
		Board board = boardRepository.findByIdAndStatus(body.getBoardId(), Status.READ.getStatus())
				.orElseThrow(NotFoundBoardException::new);
		if(!board.getBoardWriter().equals(body.getBoardWriter()))
			throw new ForbiddenRequestException();
		// 게시물 갱신
		board.updateBoard(body);
	}

	@Transactional
	public void deleteBoard(Long boardId,
							String boardWriter) {
		Board board = boardRepository.findByIdAndStatus(boardId, Status.READ.getStatus())
				.orElseThrow(NotFoundBoardException::new);
		if(!board.getBoardWriter().equals(boardWriter))
			throw new ForbiddenRequestException();
		// 데이터 삭제가 아닌 상태값 변경
		board.deleteBoard();
	}

}
