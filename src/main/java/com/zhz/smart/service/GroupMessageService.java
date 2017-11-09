package com.zhz.smart.service;

import com.zhz.smart.model.GroupMessage;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * Created by zz1987 on 17/11/1.
 */
public interface GroupMessageService {
    int insert(GroupMessage message);
}
