package com.coldwind.yingbi.utils;

import cn.hutool.core.text.CharSequenceUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ckl
 * @since 2024/9/9 18:01
 */
public class CsvUtils {

    public static String convertCsvFile(MultipartFile multipartFile) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        // 将 MultipartFile 转换为 BufferedReader 来读取 CSV 内容
        try (BufferedReader br = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                // 按照逻辑处理 CSV 的每一行
                List<String> dataList = CharSequenceUtil.split(line, ",").stream()
                        .filter(ObjectUtils::isNotEmpty)
                        .collect(Collectors.toList());

                if (isHeader) {
                    // 处理表头
                    stringBuilder.append(CharSequenceUtil.join(",", dataList)).append("\n");
                    isHeader = false;
                } else {
                    // 处理数据
                    stringBuilder.append(CharSequenceUtil.join(",", dataList)).append("\n");
                }
            }
        }

        return stringBuilder.toString();
    }
}
