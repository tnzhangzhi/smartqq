package com.zhz.smart.service.impl;

import com.zhz.smart.mapper.CategoryMapper;
import com.zhz.smart.model.Category;
import com.zhz.smart.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zz1987 on 17/11/1.
 */
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public Category selectByIndex(Integer index) {
        return categoryMapper.selectByIndex(index);
    }

    @Override
    public int insert(Category category) {
        return categoryMapper.insertSelective(category);
    }

    @Override
    public int update(Category category) {
        return categoryMapper.updateByPrimaryKeySelective(category);
    }
}
