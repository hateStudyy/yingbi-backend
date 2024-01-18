package com.coldwind.yingbi.model.dto.post;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * EL PSY CONGGROO
 */
@Data
public class PostAddRequest implements Serializable {

    /**
     * 分析目标
     */
    private String goal;

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