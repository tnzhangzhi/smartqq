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
        String response = SmartQQApi.pullMessage();
        JSONObject object = JSONObject.fromObject(response);
        int code = object.getInt("retcode");
        if(code!=0){
            logger.error("请求失败，Api返回码[{}]。你需要进入http://w.qq.com，检查是否能正常接收消息。如果可以的话点击[设置]->[退出登录]后查看是否恢复正常",code);
        }
        try {
            JSONArray array = object.getJSONArray("result");
            for (int i = 0; array != null && i < array.size(); i++) {
                JSONObject message = array.getJSONObject(i);
                String type = message.getString("poll_type");
                JSONObject jsonMsg = message.getJSONObject("value");
                JSONArray content = jsonMsg.getJSONArray("content");
                if ("message".equals(type)) {
                    Message msg = new Message();
                    msg.setContent(content.get(1).toString());
                    msg.setTime(jsonMsg.getLong("time"));
                    msg.setUserid(jsonMsg.getLong("from_uin"));
                    callback.onMessage(msg);
                } else if ("group_message".equals(type)) {
                    GroupMessage msg = new GroupMessage();
                    msg.setTime(jsonMsg.getLong("time"));
                    msg.setUserid(jsonMsg.getLong("send_uin"));
                    msg.setContent(content.get(1).toString());
                    msg.setGroupid(jsonMsg.getLong("group_code"));
                    callback.onGroupMessage(msg);
                } else if ("discu_message".equals(type)) {
                    DiscussMessage msg = new DiscussMessage();
                    msg.setContent(content.get(1).toString());
                    msg.setUserid(jsonMsg.getLong("send_uin"));
                    msg.setTime(jsonMsg.getLong("time"));
                    msg.setDiscussid(jsonMsg.getLong("did"));
                    callback.onDiscussMessage(msg);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            logger.error("接口返回"+response);
        }
    }
}
