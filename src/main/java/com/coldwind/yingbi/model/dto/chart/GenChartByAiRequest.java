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
    // todo 接入多种 ai 模型

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

    /**
     * AI 模型
     */
    private String aiModel;

    private static final long serialVersionUID = 1L;
}