package com.chals.boot.dto;

import com.chals.boot.common.Status;
import com.chals.boot.entity.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class BoardDto {
	
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SaveReqBody {
		@NotNull
		private String boardWriter;				// 작성자
		@NotNull
		private String boardTitle;				// 제목
		@NotNull
		private String boardContent;			// 내용
		
		public Board toEntity() {
			return Board.builder()
					.boardWriter(this.boardWriter)
					.boardTitle(this.boardTitle)
					.boardContent(this.boardContent)
					.status(Status.READ.getStatus())
					.build();
		}
	}
	
	@Data
	@Builder
	public static class FindAllResBody {
		private List<BoardDto.FindAll> boards;	// 게시판
		private long offset;					// 시작번호
		private long pageSize;					// 페이징크기
		private long totalCount;				// 전체게시물
		private long totalPage;					// 전체페이지
		private int page;						// 페이지
	}

	@Data
	@AllArgsConstructor
	public static class FindAll {
		private Long boardId;					// 게시판 아이디
		private String boardWriter;				// 게시판 작성자
		private String boardTitle;				// 게시판 제목
		private int readCount;					// 게시물 읽음
		private LocalDateTime createAt;			// 작성일시
	}

	@Data
	@Builder
	public static class FindResBody {
		private Long boardId;					// 게시글 아이디
		private String boardWriter;				// 게시글 작성자
		private String boardTitle;				// 게시글 제목
		private String boardContent;			// 게시글 내용
		private int readCount;					// 읽은건수
		private LocalDateTime createAt;			// 등록일시
		private LocalDateTime updateAt;			// 수정일시
		private List<Comment> comments;			// 댓글
	}

	@Data
	@Builder
	public static class Comment {
		private Long commentId;					// 댓글 아이디
		private String commentWriter;			// 댓글 작성자
		private String commentContent;			// 댓글 내용
		private LocalDateTime createAt;			// 등록일시
		private LocalDateTime updateAt;			// 수정일시
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class UpdateReqBody {
		@NotNull
		private Long boardId;					// 게시판 아이디
		@NotNull
		private String boardWriter;				// 게시판 작성자
		@NotNull
		private String boardTitle;				// 게시판 제목
		@NotNull
		private String boardContent;			// 게시판 내용
	}
}
