package com.vk.flowable.mgt.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collections;
import java.util.List;

/**
 * 将mp分页转为自定义分页信息
 * Created by zml on 2019/5/30.
 */
public class PageBuilder {

    public static <T> PageInfo<T> buildPage(long total, List<T> list) {
        return build(total, list);
    }

    public static <T> PageInfo<T> buildPage(Page<T> page) {
        return build(page.getTotal(), page.getRecords());
    }

    public static <T> PageInfo<T> buildEmpty() {
        return build(0, Collections.emptyList());
    }

    static <T> PageInfo<T> build(long total, List<T> list) {
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setTotal(total);
        pageInfo.setRows(list);
        return pageInfo;
    }

}
