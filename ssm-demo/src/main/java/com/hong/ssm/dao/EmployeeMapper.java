package com.hong.ssm.dao;


import com.hong.ssm.bean.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeMapper {
	
	public Employee getEmpById(Integer id);
	
	public List<Employee> getEmps();

}
