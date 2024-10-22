package com.coldwind.yingbi.model.dto.chart;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 编辑请求
 *
 * EL PSY CONGGROO
 */
@Data
public class ChartEditRequest implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 名称
     */
    private String name;

    /**
     * 图标数据
     */
    private String chartDate;

    /**
     * 图标类型
     */
    private String chartType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}