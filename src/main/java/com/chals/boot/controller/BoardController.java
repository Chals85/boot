package com.chals.boot.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chals.boot.common.BaseController;
import com.chals.boot.dto.BoardDto;
import com.chals.boot.dto.ResponseDto;
import com.chals.boot.service.BoardService;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/boards")
public class BoardController extends BaseController {
	
	private final BoardService boardService;
	
	@PostMapping("")
	public ResponseEntity<ResponseDto> saveBoard(@Valid @RequestBody BoardDto.SaveReqBody body) {
		boardService.saveBoard(body);
		return responseSuccess();
	}
	
	@GetMapping("")
	public ResponseEntity<ResponseDto> findAllBoard(@RequestParam String searchKey,
													@RequestParam String searchVal,
													@PageableDefault(page = 0, size = 5) Pageable pageable) {
		return responseSuccess(boardService.findAllBoard(searchKey,
														 searchVal,
														 pageable));
	}

	@GetMapping("{boardId}")
	public ResponseEntity<ResponseDto> findBoard(@PathVariable Long boardId) {
		return responseSuccess(boardService.findBoard(boardId));
	}

	@PutMapping("")
	public ResponseEntity<ResponseDto> updateBoard(@Valid @RequestBody BoardDto.UpdateReqBody body) {
		boardService.updateBoard(body);
		return responseSuccess();
	}

	@DeleteMapping("")
	public ResponseEntity<ResponseDto> deleteBoard(@RequestParam Long boardId,
												   @RequestParam String boardWriter) {
		boardService.deleteBoard(boardId,
								 boardWriter);
		return responseSuccess();
	}

}