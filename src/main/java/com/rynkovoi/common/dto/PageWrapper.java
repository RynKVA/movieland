package com.rynkovoi.common.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageWrapper<T> {

    private final List<T> content;
    private final long totalElements;
    private final int totalPages;
    private final int currentPage;
    private final boolean last;

    public PageWrapper(List<T> content, Page<?> page) {
        this.content = content;
        this.currentPage = page.getPageable().getPageNumber();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
    }
}
