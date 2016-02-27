package com.ishidai.ischedule.params;

import com.alibaba.fastjson.JSONObject;

/**
 * 任务绑定参数
 * 
 * @author kangye
 */
public class JobContent {

    /**
     * 任务绑定参数
     */
    protected JSONObject extJosnObject;

    public JSONObject getExtJosnObject() {
        return extJosnObject;
    }

    public void setExtJosnObject(JSONObject extJosnObject) {
        this.extJosnObject = extJosnObject;
    }
}
