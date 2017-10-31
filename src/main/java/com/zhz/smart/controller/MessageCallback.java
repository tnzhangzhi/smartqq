package com.zhz.smart.controller;

import com.zhz.robot.model.DiscussMessage;
import com.zhz.robot.model.GroupMessage;
import com.zhz.robot.model.Message;

/**
 * Created by zz1987 on 17/10/31.
 */
public interface MessageCallback {
    /**
     * 收到私聊消息后的回调
     * @param message
     */
    void onMessage(Message message);

    /**
     * 收到群消息后的回调
     * @param message
     */
    void onGroupMessage(GroupMessage message);

    /**
     * 收到讨论组消息后的回调
     * @param message
     */
    void onDiscussMessage(DiscussMessage message);
}
