package com.zhz.smart.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by zz1987 on 17/10/31.
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}