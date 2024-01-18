package com.coldwind.yingbi.model.vo;

import lombok.Data;

/**
 * @author ckl
 * @since 2023/7/20 14:52
 */
@Data
public class BiResponse {

    /**
     * 生成图表
     */
    private String genChart;

    /**
     * 分析结果
     */
    private String genResult;

    /**
     * 图表id
     */
    private Long chartId;
}
