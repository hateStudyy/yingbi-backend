package com.coldwind.yingbi.model.dto.postfavour;

import com.coldwind.yingbi.common.PageRequest;
import com.coldwind.yingbi.model.dto.chart.ChartQueryRequest;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 帖子收藏查询请求
 *
 * EL PSY CONGGROO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostFavourQueryRequest extends PageRequest implements Serializable {

    /**
     * 帖子查询请求
     */
    private ChartQueryRequest postQueryRequest;

    /**
     * 用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}