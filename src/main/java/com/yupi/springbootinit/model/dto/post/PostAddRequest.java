package com.yupi.springbootinit.model.dto.post;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 创建请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
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