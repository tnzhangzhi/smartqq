package com.zhz.smart.mapper;

import com.zhz.smart.model.Groups;
import com.zhz.smart.util.MyMapper;
import org.apache.ibatis.annotations.Param;

public interface GroupsMapper extends MyMapper<Groups> {
    Groups selectByGid(@Param("gid") Long gid);
}