package com.vk.flowable.mgt.rpc.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zml on 2019/5/30.
 */
public class PageDTO<T> implements Serializable {

    // 当前页
    private long pageNum;
    // 每页显示的总条数
    private long pageSize;
    // 总条数
    private long total;
    // 总页数
    private long pages;
    // 分页结果
    private List<T> list;

    public PageDTO() {

    }

    public PageDTO(Long total , List<T> list) {
        this.total = total;
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageDTO{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", pages=" + pages +
                ", list=" + list +
                '}';
    }
}
