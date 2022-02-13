package com.chals.boot.repository;

import org.springframework.data.domain.Pageable;

import com.chals.boot.dto.BoardDto;

public interface BoardRepositoryCustom {

	BoardDto.FindAllResBody findAll(String searchKey,
									String searchVal,
									Pageable pageable);
	
}
