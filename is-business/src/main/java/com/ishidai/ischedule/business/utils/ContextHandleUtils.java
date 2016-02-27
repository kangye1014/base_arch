package com.ishidai.ischedule.business.utils;

public final class ContextHandleUtils {

    private ContextHandleUtils() {
    }

    public static String replaceContentByParams(String content, String[] params) {
        for (int i = 0; i < params.length; i++) {
            content = content.replace("param" + i, params[i]);
        }
        return content;
    }
}
