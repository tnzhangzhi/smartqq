package com.zhz.smart.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by zz1987 on 17/11/7.
 */
@Configuration
public class SolrClientConfig {

    @Autowired
    private Environment environment;

    @Bean
    public SolrClient solrClient(){
        return new CloudSolrClient(environment.getRequiredProperty("spring.solr.zkHost"));
    }
}
