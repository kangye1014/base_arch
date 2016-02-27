package com.ishidai.ischedule.business.enums;

import java.util.EnumSet;

/**
 * @用户来源枚举（统一以此为准）
 * @author wangmeng
 * @time 2015年5月27日下午6:01:16
 */
public enum CommSourceEnum {

    KANIU(0, "卡牛H5", "卡牛H5 （ios，android）"), PUHUI(1, "普惠", "普惠（ios，android，网站web）"), CAR(2, "汽车之家",
            "汽车之家（ios，android，网站web）"), CARD51(3, "51信用卡", "51信用卡（ios，android，网站web）"), KANIU_OR_APP(10, "卡牛H5或者APP",
            "卡牛H5或者APP"),WEIXIN(4, "微信", "微信（ios，android，网站web）"), JIEAPP(6, "借啊APP", "借啊APP（借啊APP2.0）)");

    private final int type;
    private final String value;
    private final String desc;

    CommSourceEnum(int type, String value, String desc) {
        this.type = type;
        this.value = value;
        this.desc = desc;
    }

    public static CommSourceEnum getEnumByType(int type) {
        for (CommSourceEnum myEnum : EnumSet.allOf(CommSourceEnum.class)) {
            if (myEnum.getType() == type) {
                return myEnum;
            }
        }
        return null;
    }

    public static String getValueByType(int type) {
        CommSourceEnum commSourceEnum = getEnumByType(type);
        if (commSourceEnum != null) {
            return commSourceEnum.getValue();
        } else {
            return null;
        }
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
