package com.coldwind.yingbi.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Excel 工具类
 * @author ckl
 * @since 2023/7/19 15:57
 */
@Slf4j
public class ExcelUtils {

    /**
     * excel 转 csv
     * @param multipartFile
     * @return
     */
    public static String excelToCsv(MultipartFile multipartFile) {
        // File file = null;
        // try {
        //     file = ResourceUtils.getFile("classpath:网站数据.xlsx");
        // } catch (FileNotFoundException e) {
        //     throw new RuntimeException(e);
        // }

        List<Map<Integer, String>> list = null;
        try {
            list = EasyExcel.read(multipartFile.getInputStream())
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();
        } catch (IOException e) {
            log.error("表格处理错误");
        }

        if(CollUtil.isEmpty(list)) {
            return "";
        }
        // 转换为csv
        StringBuilder stringBuilder = new StringBuilder();
        // 读取表头
        LinkedHashMap<Integer, String> headerMap =(LinkedHashMap) list.get(0);
        List<String> headerList = headerMap.values().stream()
                .filter(ObjectUtils::isNotEmpty)
                .collect(Collectors.toList());
        stringBuilder.append(StrUtil.join(",", headerList)).append("\n");
        // 读取数据
        for (int i = 1; i < list.size(); i++) {
            LinkedHashMap<Integer, String> dataMap =(LinkedHashMap) list.get(i);
            List<String> dataList = dataMap.values().stream()
                    .filter(ObjectUtils::isNotEmpty)
                    .collect(Collectors.toList());
            stringBuilder.append(StrUtil.join(",", dataList)).append("\n");
        }
        System.out.println(list);
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        excelToCsv(null);
    }
}
