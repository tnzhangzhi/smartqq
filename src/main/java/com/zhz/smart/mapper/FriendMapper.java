package com.zhz.smart.mapper;

import com.zhz.smart.model.Friend;
import com.zhz.smart.util.MyMapper;
import org.apache.ibatis.annotations.Param;

public interface FriendMapper extends MyMapper<Friend> {
    public Friend selectByUserId(@Param("userid") Long userId);
}