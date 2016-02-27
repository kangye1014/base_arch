package com.ishidai.ischedule.business.enums;

import java.util.EnumSet;

/**
 * @note 用户划扣充值状态枚举
 * @author wangmeng
 * @date 2015年10月10日 下午5:36:41
 */
public enum UserDrawStatusEnum {

	DRAW_ING(0, "结算划扣中"), DRAW_SUCCESS(1, "结算划扣成功"), DRAW_FALSE(2, "结算划扣失败"), CORE_CHARGE_SUCCESS(3, "核心充值成功"), CORE_CHARGE_FALSE(4, "核心充值失败"), PART_DRAW(5, "部分结算划扣"), DRAW_ACCEPT_SUCCESS(6,
			"结算划扣接收成功"), NOT_DRAW_CONDITION(7, "不符合划扣条件"), PUSH_SETTLEMENT_FAIL(8, "推送结算失败"), FALSE_DELETE(9, "软删除记录,用于记录划扣操作日志");

	private final String value;
	private final int type;

	UserDrawStatusEnum(int type, String value) {
		this.type = type;
		this.value = value;
	}

	public static UserDrawStatusEnum getEnumByType(int type) {
		for (UserDrawStatusEnum myEnum : EnumSet.allOf(UserDrawStatusEnum.class)) {
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
}
