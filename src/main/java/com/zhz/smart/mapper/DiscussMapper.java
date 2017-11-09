package com.zhz.smart.mapper;

import com.zhz.smart.model.Discuss;
import com.zhz.smart.util.MyMapper;
import org.apache.ibatis.annotations.Param;

public interface DiscussMapper extends MyMapper<Discuss> {
    Discuss selectByDid(@Param("did") Long did);
}