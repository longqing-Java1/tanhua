package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;

public interface CheckItemDao {

    public void add(CheckItem checkItem);

    /**
     * 条件分页查询
     * @param queryString
     * @return
     */
    Page<CheckItem> findByCondition(String queryString);

}
