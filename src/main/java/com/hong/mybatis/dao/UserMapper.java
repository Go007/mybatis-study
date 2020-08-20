package com.hong.mybatis.dao;

import com.hong.mybatis.bean.User;

/**
 * @Description:
 * @Author wanghong
 * @Date 2020/8/20 18:02
 * @Version V1.0
 **/
public interface UserMapper {

    User getOneById(Long id);

}
