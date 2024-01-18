package com.coldwind.yingbi.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 *
 * EL PSY CONGGROO
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}