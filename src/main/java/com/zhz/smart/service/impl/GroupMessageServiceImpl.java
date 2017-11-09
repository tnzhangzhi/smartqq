package com.zhz.smart.service.impl;

import com.zhz.smart.mapper.GroupMessageMapper;
import com.zhz.smart.model.GroupMessage;
import com.zhz.smart.service.GroupMessageService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by zz1987 on 17/11/1.
 */
@Service
public class GroupMessageServiceImpl implements GroupMessageService{

    @Autowired
    GroupMessageMapper groupMessageMapper;

    @Autowired
    private SolrClient client;

    @Override
    public int insert(GroupMessage message) {
        SolrInputDocument doc = new SolrInputDocument();
        int val =groupMessageMapper.insertSelective(message);
        doc.setField("id",message.getId());
        doc.setField("content",message.getContent());
        doc.setField("gid", message.getGroupid());
        doc.setField("time",message.getTime());
        doc.setField("uid",message.getUserid());
        try {
            UpdateResponse response = client.add("smartqq",doc);
            System.out.println(response.getRequestUrl());
            System.out.println(response.toString());
            response = client.commit("smartqq");
            System.out.println(response.toString());
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return val;
    }
}
