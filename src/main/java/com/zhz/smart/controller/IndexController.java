package com.zhz.smart.controller;

import com.zhz.smart.model.DiscussMessage;
import com.zhz.smart.model.GroupMessage;
import com.zhz.smart.model.Message;
import com.zhz.smart.service.*;
import com.zhz.smart.util.SmartQQApi;
import org.apache.commons.lang.time.DateUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

/**
 * Created by zz1987 on 17/10/31.
 */
@RestController
public class IndexController {

    @Autowired
    private SolrClient client;



    @Autowired
    FriendService friendService;

    @Autowired
    DiscussService discussService;

    @Autowired
    GroupService groupService;

    @Autowired
    MessageService messageService;

    @Autowired
    GroupMessageService groupMessageService;

    @Autowired
    DiscussMessageService discussMessageService;

    @RequestMapping("/index")
    public String index(){
        return "hello world";
    }

    @RequestMapping("/friends")
    public String getFriends(){
        String info = SmartQQApi.getFriends();
        friendService.create(info);
        return info;
    }

    @RequestMapping("/group")
    public String getGroup(){
        String info = SmartQQApi.getGroupList();
        groupService.create(info);
        return info;
    }

    @RequestMapping("/discuss")
    public String getDiscuss(){
        String info = SmartQQApi.getDiscuss();
        discussService.create(info);
        return info;
    }

    @RequestMapping("/recive")
    public String reciveMsg(){
        SmartClient client = new SmartClient(new MessageCallback() {
            @Override
            public void onMessage(Message message) {
                messageService.insert(message);
            }

            @Override
            public void onGroupMessage(GroupMessage message) {
                groupMessageService.insert(message);
            }

            @Override
            public void onDiscussMessage(DiscussMessage message) {
                discussMessageService.insert(message);
            }
        });
        return "正在收发消息中";
    }

    @RequestMapping("/query")
    public String query() throws IOException, SolrServerException {

        SolrQuery query = new SolrQuery();
        query.set("q","title:中国");
        query.setHighlight(true);
        query.addHighlightField("title");
        query.setHighlightSimplePre("<<");
        query.setHighlightSimplePost(">>");
        query.setRows(20);
        query.setSort("id", SolrQuery.ORDER.desc);
        QueryResponse response = client.query("smartqq",query);
        SolrDocumentList docs = response.getResults();
        Map<String,Map<String,List<String>>> high = response.getHighlighting();
        List<Map<String,Object>> olist = new ArrayList<>();
        for(SolrDocument document : docs){
            String id = document.getFieldValue("id").toString();
            String title = document.getFieldValue("title").toString();
            Map<String,List<String>> map = high.get(id);
            List<String> list = map.get("title");
            String content = list.get(0);
            long gid = (Long)document.getFieldValue("gid");
            long time = (Long)document.getFieldValue("time");
            long uid = (Long)document.getFieldValue("uid");
            Map<String,Object> cmap =new HashMap<>();
            cmap.put("id",id);
            cmap.put("title",title);
            cmap.put("content",content);
            cmap.put("gid",gid);
            cmap.put("time",time);
            cmap.put("uid",uid);
            olist.add(cmap);
//            Iterator<Map.Entry<String,Object>> it = document.iterator();
//            while(it.hasNext()){
//                Map.Entry <String,Object> entry = it.next();
//                System.out.println("key="+entry.getKey()+",value="+entry.getValue());
//            }
        }
        return olist.toString();
    }

    @RequestMapping("/schema")
    public String editSchema(){
        //直接修改schema
//        <field name="id" type="string" indexed="true" stored="true" required="true" multiValued="false" />
//        <field name="content" type="text_ik" indexed="true" stored="true"/>
//        <field name="gid" type="plong" indexed="true" stored="true"/>
//        <field name="time" type="plong" indexed="true" stored="true"/>
//        <field name="title" type="text_ik" indexed="true" stored="true"/>
//        <field name="uid" type="plong" indexed="true" stored="true"/>
//
//        <copyField source="gid" dest="_text_"/>
//        <copyField source="uid" dest="_text_"/>
//        <copyField source="id" dest="_text_"/>
//
//        <fieldType name="text_ik" class="solr.TextField">
//        <analyzer type="index">
//        <tokenizer class="org.wltea.analyzer.lucene.IKTokenizerFactory" useSmart="false" />
//        </analyzer>
//        <analyzer type="query">
//        <tokenizer class="org.wltea.analyzer.lucene.IKTokenizerFactory" useSmart="true" />
//        </analyzer>
//        </fieldType>
        return "";
    }

    @RequestMapping("/delete")
    public String delete() throws IOException, SolrServerException {
        UpdateResponse response = client.deleteByQuery("smartqq","*:*");
        client.commit("smartqq");
        return  response.toString();
    }

    @RequestMapping("/add")
    public String add() throws IOException, SolrServerException {
        String[] titles = new String[]{"你好中国人","我的中国梦","长江长城,黄山黄河","我是中国人","日本人是狗","美国人是猪","国庆节去哪里","去哪儿网","多听FM","青团社有限公司"};
        String[] contents = new String[]{"唱一首简单的哥","刚好遇见你","你的歌声里","向天再借500年","一千零一夜","讲不出再见","夜空中最亮的星","我不是真正的快乐","成都成都","我们都一样"};
        for(int i=0;i<10;i++) {
            SolrInputDocument solrDocument = new SolrInputDocument();
            solrDocument.setField("id", i+1);
            solrDocument.setField("title", titles[i]);
            solrDocument.setField("content",contents[i]);
            solrDocument.setField("uid",i+2);
            solrDocument.setField("gid",i+4);
            solrDocument.setField("time",System.currentTimeMillis());
            client.add("smartqq",solrDocument);
        }
        UpdateResponse response = client.commit("smartqq");
        return response.toString();
    }



}
