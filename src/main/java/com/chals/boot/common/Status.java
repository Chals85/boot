package com.chals.boot.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    READ("READ", "공개"),
    DELETE("DELETE", "삭제");

    private String status;
    private String name;

    Status(String status, String name) {
        this.status = status;
        this.name = name;
    }
}
