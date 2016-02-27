package com.ishidai.ischedule.business.utils;

import java.util.UUID;

/**
 * UUID
 * 
 * @author wangjiannan
 */
public class UuidUtil {
    public static String createUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
