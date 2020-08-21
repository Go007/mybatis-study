package com.hong.mybatis.dao;

import com.hong.mybatis.bean.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @Author wanghong
 * @Date 2020/8/21 11:27
 * @Version V1.0
 **/
public interface OrderMapperDynamicSql {

    List<Order> getOrdersByConditionIf(Order order);
    List<Order> getOrdersByConditionTrim(Order order);
    List<Order> getOrdersByConditionChoose(Order order);
    List<Order> getOrdersByConditionForeach(@Param("ids") List<Long> ids);
    int updateSelective(Order order);

}
