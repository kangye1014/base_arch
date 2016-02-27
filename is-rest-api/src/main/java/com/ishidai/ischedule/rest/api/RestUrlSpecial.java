package com.ishidai.ischedule.rest.api;

import java.util.HashMap;
import java.util.Map;

public final class RestUrlSpecial {

    private RestUrlSpecial() {

    }

    private final static String SPECIAL_SIGN = "_EFOOO______";
    private final static Map<String, String> URLREPLACEMAP = new HashMap<String, String>();
    private final static Map<String, String> URLREDUCTIONMAP = new HashMap<String, String>();

    static {

        URLREPLACEMAP.put(" +", "@1");
        URLREPLACEMAP.put("\\?", "@2");
        URLREPLACEMAP.put("/", "@3");

        URLREDUCTIONMAP.put("@1", " ");
        URLREDUCTIONMAP.put("@2", "?");
        URLREDUCTIONMAP.put("@3", "/");
    }

    public static String replaceUrlSpecialChars(String source) {

        if (null == source)
            return source;
        source = source.replaceAll("@", SPECIAL_SIGN);
        for (Map.Entry<String, String> entry : URLREPLACEMAP.entrySet()) {
            source = source.replaceAll(entry.getKey(), entry.getValue());
        }

        return source;
    }

    public static String reductionUrlSpecialChars(String source) {
        if (null == source)
            return source;
        for (Map.Entry<String, String> entry : URLREDUCTIONMAP.entrySet()) {
            source = source.replaceAll(entry.getKey(), entry.getValue());
        }
        source = source.replaceAll(SPECIAL_SIGN, "@");
        return source;
    }

    public static void main(String[] args) {
        String te = "{@_@1 ad ? 0/10 * \\*} ";
        System.out.println(te.replaceAll("@", "___"));
        String trString = replaceUrlSpecialChars(te);
        System.out.println(trString);
        String trString2 = reductionUrlSpecialChars(trString);
        System.out.println(trString2);
        System.out.println(trString2.equals(te));
    }
}
