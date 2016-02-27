package com.ishidai.ischedule.business.enums;

import java.util.EnumSet;

/**
 * 结算中心银行编码number
 * 
 * @author yinjunlu
 */
public enum BankCodeEnum {

	ICBC("ICBC", "102", "中国工商银行"), CCB("CCB", "105", "中国建设银行"), ABC("ABC", "103", "中国农业银行"), BOC("BOC", "104", "中国银行"), PSBC("PSBC", "403", "中国邮政储蓄银行"), BCM("BCM", "301", "交通银行"), CMB("CMB", "308",
			"招商银行"), CEB("CEB", "303", "中国光大银行"), SPDB("SPDB", "310", "上海浦东发展银行"), HXB("HXB", "304", "华夏银行"), CGB("CGB", "307", "广东发展银行"), CNCB("CNCB", "302", "中信银行"), CIB("CIB", "309", "兴业银行"), CMBC(
			"CMBC", "305", "中国民生银行"), SDB("SDB", "0000307", "深圳发展银行"), PAB("PAB", "307", "平安银行股份有限公司"), BOB("BOB", "04031000", "北京银行"), BOS("BOS", "4012900", "上海银行"), NBCB("NBCB", "4083320", "宁波银行"), BOG(
			"BOG", "4135810", "广州银行"), HCCB("HCCB", "4233310", "杭州银行"), CBHB("CBHB", "318", "渤海银行"), HSBANK("HSBANK", "319", "徽商银行"), CZB("CZB", "316", "浙商银行"), CSCB("CSCB", "4615510", "长沙银行"), NJCB(
			"NJCB", "4243010", "南京银行"), WZCB("WZCB", "4123330", "温州市商业银行");

	private final String code;
	private final String number;
	private final String value;

	BankCodeEnum(String code, String number, String value) {
		this.code = code;
		this.number = number;
		this.value = value;
	}

	public static BankCodeEnum getEnumByCode(String code) {
		for (BankCodeEnum myEnum : EnumSet.allOf(BankCodeEnum.class)) {
			if (myEnum.getCode().equals(code)) {
				return myEnum;
			}
		}
		return null;
	}

	public static String getNumberByCode(String code) {
		BankCodeEnum BankCodeEnum = getEnumByCode(code);
		if (BankCodeEnum != null) {
			return BankCodeEnum.getNumber();
		} else {
			return null;
		}
	}

	public static BankCodeEnum getEnumByValue(String value, boolean contain) {
		for (BankCodeEnum myEnum : EnumSet.allOf(BankCodeEnum.class)) {
			boolean equals = contain ? myEnum.getValue().contains(value) : myEnum.getValue().equals(value);
			if (equals) {
				return myEnum;
			}
		}
		return null;
	}

	public String getValue() {
		return value;
	}

	public String getCode() {
		return code;
	}

	public String getNumber() {
		return number;
	}

}
