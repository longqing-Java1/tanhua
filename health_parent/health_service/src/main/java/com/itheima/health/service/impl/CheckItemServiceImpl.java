package com.itheima.health.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        //新增
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //判断是否有条件，有则使用模糊查询，【注意取反 ！】
        if (!StringUtils.isEmptyOrWhitespaceOnly(queryPageBean.getQueryString())){
            //有查询条件，拼接%
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //第二种，Mapper接口方式的调用，推荐使用这种方式。ThreadLocale
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //...
        Page<CheckItem> page = checkItemDao.findByCondition(queryPageBean.getQueryString());
        //获取总记录数
        long total  = page.getTotal();
        List<CheckItem> checkItems = page.getResult();
        return new PageResult<CheckItem>(total,checkItems);
    }
}
