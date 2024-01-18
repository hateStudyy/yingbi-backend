package com.coldwind.yingbi.model.enums;

/**
 * @author ckl
 * @since 2023/7/23 14:52
 */

public enum ChartStatusEnum {

    WAIT("wait"),
    RUNNING("running"),
    SUCCEED("succeed"),
    FAILED("failed");

    private final String status;

    ChartStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
