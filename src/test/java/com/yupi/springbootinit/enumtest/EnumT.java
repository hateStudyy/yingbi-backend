package com.yupi.springbootinit.enumtest;

import com.yupi.springbootinit.model.enums.ChartStatusEnum;
import org.junit.jupiter.api.Test;

/**
 * @author ckl
 * @since 2023/7/23 15:11
 */
public class EnumT {
    @Test
    public void test1() {
        System.out.println(ChartStatusEnum.RUNNING);
    }
}
