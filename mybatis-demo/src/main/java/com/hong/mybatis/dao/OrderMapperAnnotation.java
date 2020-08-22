package com.hong.mybatis.dao;

import com.hong.mybatis.bean.Order;
import org.apache.ibatis.annotations.Select;

public interface OrderMapperAnnotation {
	
	@Select("select * from t_order where id=#{id}")
	Order getById(Integer id);
}
