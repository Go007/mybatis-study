package com.hong.mybatis.bean;

import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * @author wanghong
 * @date 2020/08/19 22:34
 **/
@Alias("order")
public class Order {
    private Long id;
    private String orderNum;
    private Long userId;
    private String orderStatus;
    private String orderContent;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private User user;

    public Order(){

    }

    public Order(String orderNum, Long userId, String orderStatus, String orderContent) {
        this.orderNum = orderNum;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.orderContent = orderContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNum='" + orderNum + '\'' +
                ", userId=" + userId +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderContent='" + orderContent + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", user=" + user +
                '}';
    }
}
