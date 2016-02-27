package com.ishidai.ischedule.business.utils;

public final class ConstantSet {

    private ConstantSet() {

    }

    public final static String SOURCE_CODE = "333";

    /**
     * 用户人工审核不通过 5天后放弃借款
     */
    public static final long MODEL_CONSTANTS_ABANDON_DAYS = 24 * 5;

}
