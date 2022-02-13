package com.chals.boot.repository;

import com.chals.boot.common.Status;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.chals.boot.dto.BoardDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.chals.boot.entity.QBoard.board;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
	
	private final JPAQueryFactory factory;
	
	public BoardDto.FindAllResBody findAll(String searchKey,
												 String searchVal,
												 Pageable pageable) {

		BooleanBuilder isBuilder = new BooleanBuilder();
		buildBoardSearch(isBuilder, searchKey, searchVal);
		isBuilder.and(board.status.eq(Status.READ.getStatus()));

		List<BoardDto.FindAll> boards =
				factory.select(Projections.constructor(BoardDto.FindAll.class,
								board.id.as("boardId"),
								board.boardWriter.as("boardWriter"),
								board.boardTitle.as("boardTitle"),
								board.readCount.as("readCount"),
								board.createAt.as("createAt")
						))
						.from(board)
						.where(isBuilder)
						.offset(pageable.getOffset())
						.limit(pageable.getPageSize())
						.fetch();

		long boardCount =
				factory.selectFrom(board)
						.where(board.status.eq(Status.READ.getStatus()))
						.fetch().size();
		return BoardDto.FindAllResBody.builder()
				.boards(boards)
				.offset(pageable.getOffset() + 1)
				.pageSize(pageable.getPageSize())
				.page(pageable.getPageNumber() + 1)
				.totalCount(boardCount)
				.totalPage(boardCount / pageable.getPageSize())
				.build();
	}

	public void buildBoardSearch(BooleanBuilder builder,
								 String searchKey,
								 String searchVal) {
		switch (searchKey) {
			case "WRITER":
				builder.and(board.boardWriter.contains(searchVal));
				break;
			case "TITLE":
				builder.and(board.boardTitle.contains(searchVal));
				break;
			case "CONTENT":
				builder.and(board.boardContent.contains(searchVal));
				break;
		}

	}
}
