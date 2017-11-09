package com.zhz.smart.service;

import com.zhz.smart.model.Category;

/**
 * Created by zz1987 on 17/11/1.
 */
public interface CategoryService {
    Category selectByIndex(Integer index);

    int insert(Category category);

    int update(Category category);
}
