package com.zhz.smart.controller;

import com.zhz.smart.model.DiscussMessage;
import com.zhz.smart.model.GroupMessage;
import com.zhz.smart.model.Message;
import com.zhz.smart.util.SmartQQApi;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by zz1987 on 17/10/31.
 */
public class SmartClient {

    Logger logger = LoggerFactory.getLogger(SmartClient.class);

    boolean pollStarted;

    public SmartClient(MessageCallback callback) {
        SmartQQApi.login();
        if (callback != null) {
            this.pollStarted = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (!pollStarted) {
                            return;
                        }
                        try {
                            pollMessage(callback);
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }
                }
            }).start();
        }
    }

    public void pollMessage(MessageCallback callback){
        logger.info("开始接收消息");
        JSONObject r = new JSONObject();
        r.put("ptwebqq", SmartQQApi.ptwebqq);
        r.put("clientid", SmartQQApi.clientId);
        r.put("psessionid", SmartQQApi.psessionid);
        r.put("key", "");

        String response = SmartQQApi.pullMessage(r);
        JSONArray array = getJsonArrayResult(response);
        for (int i = 0; array != null && i < array.size(); i++) {
            JSONObject message = array.getJSONObject(i);
            String type = message.getString("poll_type");
            JSONObject jsonMsg = message.getJSONObject("value");
            if ("message".equals(type)) {
                Message msg = new Message();
                callback.onMessage(msg);
            } else if ("group_message".equals(type)) {
                GroupMessage msg = new GroupMessage();
                callback.onGroupMessage(msg);
            } else if ("discu_message".equals(type)) {
                DiscussMessage msg = new DiscussMessage();
                callback.onDiscussMessage(msg);
            }
        }
    }

    private JSONArray getJsonArrayResult(String response) {
        JSONObject json = JSONObject.fromObject(response);
        Integer retCode = json.getInt("retcode");
        if (retCode == null || retCode != 0) {
            if (retCode != null && retCode == 103) {
                logger.error("请求失败，Api返回码[103]。你需要进入http://w.qq.com，检查是否能正常接收消息。如果可以的话点击[设置]->[退出登录]后查看是否恢复正常");
            } else {
                throw new RuntimeException(String.format("请求失败，Api返回码[%d]", retCode));
            }
        }
        return json.getJSONArray("result");
    }
}
