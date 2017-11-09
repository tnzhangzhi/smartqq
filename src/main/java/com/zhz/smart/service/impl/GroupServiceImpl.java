package com.zhz.smart.service.impl;

import com.zhz.smart.mapper.GroupsMapper;
import com.zhz.smart.model.Groups;
import com.zhz.smart.service.GroupService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zz1987 on 17/11/1.
 */
@Service
public class GroupServiceImpl implements GroupService {

    Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    GroupsMapper groupsMapper;

    @Override
    public void create(String result) {
        JSONObject object = JSONObject.fromObject(result);
        int retcode = object.getInt("retcode");
        if(retcode == 0) {
            object = object.getJSONObject("result");
            JSONArray categorys = object.getJSONArray("gnamelist");
            for (int i = 0; i < categorys.size(); i++) {
                JSONObject item = categorys.getJSONObject(i);
                Groups discuss = new Groups();
                discuss.setName(item.getString("name"));
                discuss.setCode(item.getLong("code"));
                discuss.setFlag(item.getLong("flag"));
                discuss.setGid(item.getLong("gid"));
                Groups old = groupsMapper.selectByGid(discuss.getGid());
                if (old == null) {
                    groupsMapper.insertSelective(discuss);
                } else {
                    discuss.setId(old.getId());
                    groupsMapper.updateByPrimaryKeySelective(discuss);
                }
            }
        }else{
            logger.error("接口返回失败"+result);
        }
    }
}
