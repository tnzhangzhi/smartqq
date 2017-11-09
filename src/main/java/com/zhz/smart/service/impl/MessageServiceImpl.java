package com.zhz.smart.service.impl;

import com.zhz.smart.mapper.MessageMapper;
import com.zhz.smart.model.Message;
import com.zhz.smart.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zz1987 on 17/11/1.
 */
@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    MessageMapper messageMapper;

    @Override
    public int insert(Message message) {
        return messageMapper.insertSelective(message);
    }
}
