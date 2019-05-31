package com.vk.flowable.mgt.domain;

import lombok.Data;

import java.util.Date;

/**
 * 评论
 * Created by zml on 2019/5/29.
 */
@Data
public class CommentInfo {

    private String userId;

    private String userName;

    private String content;

    private Date time;

}
