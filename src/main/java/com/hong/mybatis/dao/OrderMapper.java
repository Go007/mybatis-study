package com.hong.mybatis.dao;

import com.hong.mybatis.bean.Order;

/**
 * @author wanghong
 * @date 2020/08/19 22:36
 **/
public interface OrderMapper {

    Order getById(Long id);
}
