package com.vk.flowable.mgt.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zml on 2018/10/11.
 */
@Data
@ToString
public class BaseEntity implements Serializable {

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 数据版本
     */
    @Version
    private Long version;
    /**
     * 逻辑删除标志（0.未删除，1.已删除）
     */
    @TableLogic
    private Integer deleted;

}
