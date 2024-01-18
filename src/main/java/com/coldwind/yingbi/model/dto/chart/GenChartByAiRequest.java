package com.coldwind.yingbi.model.dto.chart;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传请求
 *
 * EL PSY CONGGROO
 */
@Data
public class GenChartByAiRequest implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 目标
     */
    private String goal;

    /**
     * 图标类型
     */
    private String chartType;

    private static final long serialVersionUID = 1L;
}