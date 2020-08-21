package com.hong.mybatis.bean;

import java.util.List;

/**
 * @Description:
 * @Author wanghong
 * @Date 2020/8/20 17:16
 * @Version V1.0
 **/
public class User {
    private Long id;
    private String name;

    private List<Order> orderList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", orderList=" + orderList +
                '}';
    }
}
