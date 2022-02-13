package com.chals.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chals.boot.entity.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    /**
     * 등록 게시물 조회
     * @param boardId 게시물 아이디
     * @param Status 게시물 상태(읽기 허용되어있는 항목만)
     * @return 게시물
     */
    Optional<Board> findByIdAndStatus(Long boardId,
                                      String Status);

}
