package com.zhz.smart.service.impl;

import com.zhz.smart.mapper.DiscussMapper;
import com.zhz.smart.model.Discuss;
import com.zhz.smart.service.DiscussService;
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
public class DiscussServiceImpl implements DiscussService{

    Logger logger = LoggerFactory.getLogger(DiscussServiceImpl.class);

    @Autowired
    DiscussMapper discussMapper;

    @Override
    public void create(String result) {
        JSONObject object = JSONObject.fromObject(result);
        int retcode = object.getInt("retcode");
        if(retcode == 0) {
            object = object.getJSONObject("result");
            JSONArray categorys = object.getJSONArray("dnamelist");
            for (int i = 0; i < categorys.size(); i++) {
                JSONObject item = categorys.getJSONObject(i);
                Discuss discuss = new Discuss();
                discuss.setName(item.getString("name"));
                discuss.setDid(item.getLong("did"));
                Discuss old = discussMapper.selectByDid(discuss.getDid());
                if (old == null) {
                    discussMapper.insertSelective(discuss);
                } else {
                    discuss.setId(old.getId());
                    discussMapper.updateByPrimaryKeySelective(discuss);
                }
            }
        }else{
            logger.error("接口返回失败"+result);
        }
    }
}
