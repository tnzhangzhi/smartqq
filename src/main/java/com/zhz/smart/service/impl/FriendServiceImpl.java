package com.zhz.smart.service.impl;

import com.zhz.smart.mapper.FriendMapper;
import com.zhz.smart.model.Category;
import com.zhz.smart.model.Friend;
import com.zhz.smart.service.CategoryService;
import com.zhz.smart.service.FriendService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zz1987 on 17/11/1.
 */
@Service
public class FriendServiceImpl implements FriendService{

    Logger logger = LoggerFactory.getLogger(FriendServiceImpl.class);

    @Autowired
    FriendMapper friendMapper;

    @Autowired
    CategoryService categoryService;

    @Override
    @Transactional
    public void create(String result) {
        JSONObject object = JSONObject.fromObject(result);
        int retcode = object.getInt("retcode");
        if(retcode == 0) {
            object = object.getJSONObject("result");
            Map<Long, Friend> friendMap = new HashMap<>();
            JSONArray categorys = object.getJSONArray("categories");
            for (int i = 0; i < categorys.size(); i++) {
                JSONObject item = categorys.getJSONObject(i);
                Category category = new Category();
                category.setIndexNum(item.getInt("index"));
                category.setName(item.getString("name"));
                category.setSort(item.getInt("sort"));

                Category old = categoryService.selectByIndex(category.getIndexNum());
                if (old == null) {
                    categoryService.insert(category);
                } else {
                    category.setId(old.getId());
                    categoryService.update(category);
                }
            }
            JSONArray info = object.getJSONArray("info");
            for (int i = 0; i < info.size(); i++) {
                JSONObject item = info.getJSONObject(i);
                Friend friend = new Friend();
                friend.setUserid(item.getLong("uin"));
                friend.setNickname(item.getString("nick"));
                friendMap.put(friend.getUserid(), friend);
            }

            JSONArray marknames = object.getJSONArray("marknames");
            for (int i = 0; i < marknames.size(); i++) {
                JSONObject item = marknames.getJSONObject(i);
                friendMap.get(item.getLong("uin")).setMarkname(item.getString("markname"));
            }

            JSONArray vipinfo = object.getJSONArray("vipinfo");
            for (int i = 0; vipinfo != null && i < vipinfo.size(); i++) {
                JSONObject item = vipinfo.getJSONObject(i);
                Friend friend = friendMap.get(item.getLong("u"));
                friend.setVip(item.getInt("is_vip"));
                friend.setViplevel(item.getInt("vip_level"));
            }
            Collection<Friend> collection = friendMap.values();
            Iterator<Friend> iterator = collection.iterator();
            while (iterator.hasNext()) {
                Friend friend = iterator.next();
                Friend old = friendMapper.selectByUserId(friend.getUserid());
                if (old == null) {
                    friendMapper.insertSelective(friend);
                } else {
                    friend.setId(old.getId());
                    friendMapper.updateByPrimaryKeySelective(friend);
                }
            }
        }else {
            logger.error("接口返回失败:"+result);
        }
    }
}
