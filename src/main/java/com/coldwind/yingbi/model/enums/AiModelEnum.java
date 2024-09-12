package com.coldwind.yingbi.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色枚举
 *
 * EL PSY CONGGROO
 */
public enum AiModelEnum {

    CHATGPT("ChatGPT", "chatgpt"),
    YUCONGMING("鱼聪明", "yucongming");


    private final String text;

    private final String value;

    AiModelEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    // 静态方法检查是否存在
    public static boolean isValidValue(String value) {
        for (AiModelEnum model : AiModelEnum.values()) {
            if (model.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static AiModelEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (AiModelEnum anEnum : AiModelEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
