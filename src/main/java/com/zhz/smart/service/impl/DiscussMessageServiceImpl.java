package com.zhz.smart.service.impl;

import com.zhz.smart.mapper.DiscussMessageMapper;
import com.zhz.smart.model.DiscussMessage;
import com.zhz.smart.service.DiscussMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zz1987 on 17/11/1.
 */
@Service
public class DiscussMessageServiceImpl implements DiscussMessageService{

    @Autowired
    DiscussMessageMapper discussMessageMapper;
    @Override
    public int insert(DiscussMessage message) {
        return discussMessageMapper.insertSelective(message);
    }
}
