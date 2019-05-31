package com.vk.flowable.mgt.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by zml on 2019/5/30.
 */
@Data
public class HistoricProcessInfo {

    private Date startTime;

    private Date endTime;

    private String businessKey;

    private String startUserId;

    private String startActivityId;

    private String deleteReason;

    private int version;

}
