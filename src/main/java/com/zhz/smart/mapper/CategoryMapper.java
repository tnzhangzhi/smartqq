package com.zhz.smart.mapper;

import com.zhz.smart.model.Category;
import com.zhz.smart.util.MyMapper;
import org.apache.ibatis.annotations.Param;

public interface CategoryMapper extends MyMapper<Category> {
    Category selectByIndex(@Param("indexNum") Integer index);
}