package com.chals.boot.controller;

import com.chals.boot.common.Status;
import com.chals.boot.entity.Board;
import com.chals.boot.entity.Comment;
import com.chals.boot.repository.BoardRepository;
import com.chals.boot.repository.CommentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected CommentRepository commentRepository;
    @Autowired
    protected BoardRepository boardRepository;

    protected Long boardId;
    protected Long commentId;
    protected Board board;
    protected Comment comment;

    @BeforeEach
    public void beforeEach() throws Exception {
        board = Board.builder()
                .boardWriter("손흥민")
                .boardTitle("게시판테스트")
                .boardContent("게시판테스트 내용")
                .status(Status.READ.getStatus())
                .build();
        boardRepository.save(board);

        // 댓글 등록
        comment = Comment.builder()
                .board(board)
                .commentWriter("차범근")
                .commentContent("흥만아!! 가즈아!!!")
                .status(Status.READ.getStatus())
                .build();
        commentRepository.save(comment);

        boardId = board.getId();
        commentId = comment.getId();
    }
}
