package com.ishidai.ischedule.business.enums;

import java.util.EnumSet;

/**
 * @author yinjunlu
 */
public enum OperationEnum {
	OVERDUE_REPAY(1, "REPAY_OVERDUE_REPAY", "贷后逾期还款"), KN_CASH(2, "JIEA_KN_CASH", "卡牛提现"), APP_CASH(3, "JIEA_APP_CASH", "app提现"), DRAW_CHARGE(4, "JIEA_DRAW_CHARGE", "划扣充值");
	private final int type;
	private final String code;
	private final String value;

	OperationEnum(int type, String code, String value) {
		this.type = type;
		this.code = code;
		this.value = value;
	}

	public static OperationEnum getEnumByCode(String code) {
		for (OperationEnum myEnum : EnumSet.allOf(OperationEnum.class)) {
			if (myEnum.getCode().equals(code)) {
				return myEnum;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public int getType() {
		return type;
	}

}
