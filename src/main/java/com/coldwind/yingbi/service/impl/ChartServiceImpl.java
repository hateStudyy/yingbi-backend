package com.coldwind.yingbi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coldwind.yingbi.model.entity.Chart;
import com.coldwind.yingbi.service.ChartService;
import com.coldwind.yingbi.mapper.ChartMapper;
import org.springframework.stereotype.Service;

/**
* @author 123
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2023-07-17 17:06:58
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService {

}




