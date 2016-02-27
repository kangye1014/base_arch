package com.ishidai.ischedule.business.enums;

import java.util.EnumSet;

/**
 * @app客户端来源枚举
 * @author wangmeng
 * @time 2015年5月27日下午6:31:06
 */
public enum CommAppSourceEnum {

    WEB(1, "网站", "网站"), IOS(2, "苹果手机", "苹果手机"), ANDROID(3, "安卓手机", "安卓手机"), IOSPAD(4, "苹果Pad", "苹果Pad"), ANDROIDPAD(5,
            "安卓Pad", "安卓Pad");

    private final int type;
    private final String value;
    private final String desc;

    CommAppSourceEnum(int type, String value, String desc) {
        this.type = type;
        this.value = value;
        this.desc = desc;
    }

    public static CommAppSourceEnum getEnumByType(int type) {
        for (CommAppSourceEnum myEnum : EnumSet.allOf(CommAppSourceEnum.class)) {
            if (myEnum.getType() == type) {
                return myEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

}
