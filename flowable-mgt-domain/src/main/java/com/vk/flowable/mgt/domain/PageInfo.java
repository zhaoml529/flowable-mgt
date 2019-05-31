package com.vk.flowable.mgt.domain;

import lombok.Data;

import java.util.List;

/**
 * 分页
 * @param <T>
 */
@Data
public class PageInfo<T> {

    private long total;  // 共多少条

    private List<T> rows;

    public PageInfo() {

    }

    public PageInfo(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
