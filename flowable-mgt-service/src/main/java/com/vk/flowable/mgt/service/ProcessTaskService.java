package com.vk.flowable.mgt.service;

import com.vk.flowable.mgt.domain.ProcessTask;

import java.util.List;

/**
 * Created by zml on 2019/5/16.
 */
public interface ProcessTaskService {

    List<ProcessTask> findTodoTask(Long userId, Integer limit, Integer offset);
}
