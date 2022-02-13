package com.chals.boot.controller;

import com.chals.boot.common.Status;
import com.chals.boot.dto.BoardDto;
import com.chals.boot.entity.Board;
import com.chals.boot.entity.Comment;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

class BoardControllerTest extends BaseControllerTest {

    @Test
    @Order(1)
    public void 게시글_등록() throws Exception {

        // 데이터 생성
        BoardDto.SaveReqBody body = BoardDto.SaveReqBody.builder()
                .boardWriter("손흥민")
                .boardTitle("게시판테스트")
                .boardContent("게시판 테스트 내용")
                .build();

        // 게시글 등록
        mockMvc.perform(post("/v1/boards")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body))
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-board-one",
                        requestFields(
                                fieldWithPath("boardWriter").type(JsonFieldType.STRING).description("작성자"),
                                fieldWithPath("boardTitle").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("boardContent").type(JsonFieldType.STRING).description("내용")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과")
                        )
                ));

        // 검증
        Optional<Board> board = boardRepository.findById(2L);

        // 검증
        assertTrue(board.isPresent());
        assertEquals(board.get().getId(), 2L);

    }

    @Test
    @Order(2)
    public void 게시글_목록조회() throws Exception {

        // 게시글 목록 조회
        mockMvc.perform(get("/v1/boards")
                    .contentType(MediaType.TEXT_HTML)
                    .queryParam("searchKey", "WRITER")
                    .queryParam("searchVal", "손흥민")
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                // Response 검색 응답 검증
                .andExpect(jsonPath("$.data.boards[0].boardWriter").value(board.getBoardWriter()))
                .andExpect(jsonPath("$.data.boards[0].boardTitle").value(board.getBoardTitle()))
                .andDo(document("get-board-all",
                        requestParameters(
                                parameterWithName("searchKey").description("검색조건 키"),
                                parameterWithName("searchVal").description("검색입력 값")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과"),
                                fieldWithPath("data.boards[0].boardId").type(JsonFieldType.NUMBER).description("게시판 아이디"),
                                fieldWithPath("data.boards[0].boardWriter").type(JsonFieldType.STRING).description("작성자"),
                                fieldWithPath("data.boards[0].boardTitle").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.boards[0].readCount").type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("data.boards[0].createAt").type(JsonFieldType.STRING).description("등록일시"),
                                fieldWithPath("data.offset").type(JsonFieldType.NUMBER).description("시작페이지"),
                                fieldWithPath("data.pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                fieldWithPath("data.totalCount").type(JsonFieldType.NUMBER).description("전체 게시물"),
                                fieldWithPath("data.totalPage").type(JsonFieldType.NUMBER).description("전체 페이지"),
                                fieldWithPath("data.page").type(JsonFieldType.NUMBER).description("현재 페이지")
                        )
                ));
    }

    @Test
    @Order(3)
    public void 게시글_상세조회() throws Exception {

        // 게시글 상세조회
        mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/boards/{boardId}", board.getId())
                    .contentType(MediaType.TEXT_HTML)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                // Response 검색 응답 검증
                .andExpect(jsonPath("$.data.boardWriter").value(board.getBoardWriter()))
                .andExpect(jsonPath("$.data.boardTitle").value(board.getBoardTitle()))
                .andExpect(jsonPath("$.data.boardContent").value(board.getBoardContent()))
                .andDo(document("get-board-one",
                        pathParameters(
                                parameterWithName("boardId").description("게시판 아이디")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과"),
                                fieldWithPath("data.boardId").type(JsonFieldType.NUMBER).description("게시물 아이디"),
                                fieldWithPath("data.boardWriter").type(JsonFieldType.STRING).description("게시물 작성자"),
                                fieldWithPath("data.boardTitle").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("data.boardContent").type(JsonFieldType.STRING).description("게시물 내용"),
                                fieldWithPath("data.readCount").type(JsonFieldType.NUMBER).description("게시물 읽은 수"),
                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("작성일시"),
                                fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("수정일시"),
                                fieldWithPath("data.comments[0].commentId").type(JsonFieldType.NUMBER).description("게시물 댓글아이디"),
                                fieldWithPath("data.comments[0].commentWriter").type(JsonFieldType.STRING).description("게시물 댓글작성자"),
                                fieldWithPath("data.comments[0].commentContent").type(JsonFieldType.STRING).description("게시물 댓글내용"),
                                fieldWithPath("data.comments[0].createAt").type(JsonFieldType.STRING).description("게시물 댓글 등록일시"),
                                fieldWithPath("data.comments[0].updateAt").type(JsonFieldType.STRING).description("게시물 댓글 수정일시")
                        )
                ))
                ;
    }

    @Test
    @Order(4)
    public void 게시글_수정() throws Exception {
        BoardDto.UpdateReqBody body = BoardDto.UpdateReqBody.builder()
                .boardId(boardId)
                .boardWriter("손흥민")
                .boardTitle("게시글수정")
                .boardContent("게시글수정내용")
                .build();

        // 게시글 수정
        mockMvc.perform(put("/v1/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("put-board-one",
                        requestFields(
                                fieldWithPath("boardId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("boardWriter").type(JsonFieldType.STRING).description("작성자"),
                                fieldWithPath("boardTitle").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("boardContent").type(JsonFieldType.STRING).description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과")
                        )
                ));

        // 갱신 데이터 조회
        Optional<Board> resultBoard = boardRepository.findById(boardId);

        // 검증
        assertTrue(resultBoard.isPresent());
        assertEquals(body.getBoardTitle(), resultBoard.get().getBoardTitle());
        assertEquals(body.getBoardContent(), resultBoard.get().getBoardContent());
    }

    @Test
    @Order(5)
    public void 게시글_삭제() throws Exception {

        // 게시글 삭제
        mockMvc.perform(delete("/v1/boards")
                    .contentType(MediaType.TEXT_HTML)
                    .queryParam("boardId", boardId.toString())
                    .queryParam("boardWriter", board.getBoardWriter())
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("delete-board-one",
                        requestParameters(
                                parameterWithName("boardId").description("게시판 아이디"),
                                parameterWithName("boardWriter").description("게시판 작성자")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과")
                        )
                ));

        // 삭제 데이터 조회
        Optional<Board> resultBoard = boardRepository.findById(boardId);
        Optional<Comment> resultComment = commentRepository.findById(comment.getId());

        // 검증
        assertTrue(resultBoard.isPresent());
        assertTrue(resultComment.isPresent());
        assertEquals(resultBoard.get().getStatus(), Status.DELETE.getStatus());
        assertEquals(resultComment.get().getStatus(), Status.DELETE.getStatus());
    }


}