package com.thezz9.schedule.dto;

import lombok.Getter;

@Getter
public class Paging {

    private final int pageIndex;
    private final int pageSize;
    private final int pageOffset;

    public Paging (int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.pageOffset = pageIndex * pageSize;
    }

}
