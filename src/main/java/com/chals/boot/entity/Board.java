package com.chals.boot.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.persistence.Table;

import com.chals.boot.common.BaseEntity;

import com.chals.boot.common.Status;
import com.chals.boot.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "board")
public class Board extends BaseEntity {
	
	@Column(name = "board_writer", nullable = false)
	private String boardWriter;				// 작성
	
	@Column(name = "board_title", nullable = false)
	private String boardTitle;				// 제목
	
	@Column(name = "board_content", nullable = false)
	private String boardContent;			// 내용
	
	@Column(name = "read_count")
	private int readCount;					// 읽은수

	@Column(name = "status", nullable = false)
	private String status;					// 게시글 상태

	@OneToMany(mappedBy = "board")
	private List<Comment> boardComments;

	/**
	 * 게시판 내용 갱신
	 * @param body 제목, 내용 객체
	 */
	public void updateBoard(BoardDto.UpdateReqBody body) {
		this.boardTitle = body.getBoardTitle();
		this.boardContent = body.getBoardContent();
		this.updateAt = LocalDateTime.now();
	}

	/**
	 * 게시판 삭제(상태값 변경)
	 */
	public void deleteBoard() {
		this.status = Status.DELETE.getStatus();
		this.boardComments.forEach(Comment::deleteComment);
	}

	/**
	 * 방문자 게시판 읽기 추가
	 */
	public void visitBoardRead() {
		this.readCount += 1;
	}

}
