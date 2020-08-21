package com.hong.mybatis.dao;

import com.hong.mybatis.bean.Order;

import java.util.List;

/**
 * @Description:
 * @Author wanghong
 * @Date 2020/8/20 17:07
 * @Version V1.0
 **/
public interface OrderMapperPlus {

    Order getOne(Long id);

    Order getOneWithUserName(Long id);

    Order getOneWithUserName2(Long id);

    Order getOneByStep(Long id);

   List<Order> getOrderListByUserId(Long userId);
}
