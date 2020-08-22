package com.hong;

import com.hong.mbg.bean.Employee;
import com.hong.mbg.bean.EmployeeExample;
import com.hong.mbg.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void testMbg() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        InputStream in = Resources.getResourceAsStream("mbg.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(in);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testMyBatis3Simple() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            List<Employee> list = mapper.selectByExample(null);
            for (Employee employee : list) {
                System.out.println(employee.getId());
            }
        }finally{
            openSession.close();
        }
    }

    @Test
    public void testMyBatis3() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            //xxxExample就是封装查询条件的
            //1、查询所有
            //List<Employee> emps = mapper.selectByExample(null);
            //2、查询员工名字中有e字母的，和员工性别是1的
            //封装员工查询条件的example
            EmployeeExample example = new EmployeeExample();
            //创建一个Criteria，这个Criteria就是拼装查询条件
            //select id, last_name, email, gender, d_id from tbl_employee
            //WHERE ( last_name like ? and gender = ? ) or email like "%e%"
            EmployeeExample.Criteria criteria = example.createCriteria();
            criteria.andLastNameLike("%s%");
            criteria.andGenderEqualTo("1");

            EmployeeExample.Criteria criteria2 = example.createCriteria();
            criteria2.andEmailLike("%qq%");
            example.or(criteria2);

            List<Employee> list = mapper.selectByExample(example);
            for (Employee employee : list) {
                System.out.println(employee.getId());
            }

        }finally{
            openSession.close();
        }
    }
}
