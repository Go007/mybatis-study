package com.hong.mybatis.dao;

import com.hong.mybatis.bean.Order;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wanghong
 * @date 2020/08/19 22:36
 **/
public interface OrderMapper {

    Order getById(Long id);

    int addOne(Order order);

    boolean updateOne(Order order);

    boolean deleteById(Long id);

    //多条记录封装一个map：Map<Integer,Order>:键是这条记录的主键，值是记录封装后的javaBean
    //@MapKey:告诉mybatis封装这个map的时候使用哪个属性作为map的key
    @MapKey("orderNum")
    Map<String, Order> getListByReturnMap(String userId);

    List<Order> getListByUserId(String userId);

    //返回一条记录的map；key就是列名，值就是对应的值
    Map<String, Object> getOneByReturnMap(Long id);

    Order getOne(Long id);

    Order getOneByMap(Map<String, Object> map);

    Order getOneByCondition(@Param("orderStatus") String orderStatus,@Param("orderNum") String orderNum);
}
