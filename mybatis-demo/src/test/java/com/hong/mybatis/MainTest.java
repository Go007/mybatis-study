package com.hong.mybatis;

import com.hong.mybatis.bean.Order;
import com.hong.mybatis.bean.User;
import com.hong.mybatis.dao.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author wanghong
 * @date 2020/08/19 22:41
 * <p>
 * 1、接口式编程
 * 原生：		Dao		====>  DaoImpl
 * mybatis：	Mapper	====>  xxMapper.xml
 * <p>
 * 2、SqlSession代表和数据库的一次会话；用完必须关闭；
 * 3、SqlSession和connection一样都是非线程安全。每次使用都应该去获取新的对象。
 * 4、mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。
 * （将接口和xml进行绑定）
 * OrderMapper mapper = openSession.getMapper(OrderMapper.class);
 * 5、两个重要的配置文件：
 * mybatis的全局配置文件：包含数据库连接池信息，事务管理器信息等...系统运行环境信息
 * sql映射文件：保存了每一个sql语句的映射信息：将sql抽取出来。
 */
public class MainTest {
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 1、根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象 有数据源一些运行环境信息
     * 2、sql映射文件；配置了每一个sql，以及sql的封装规则等。
     * 3、将sql映射文件注册在全局配置文件中
     * 4、写代码：
     * 1）、根据全局配置文件得到SqlSessionFactory；
     * 2）、使用sqlSession工厂，获取到sqlSession对象使用他来执行增删改查
     * 一个sqlSession就是代表和数据库的一次会话，用完关闭
     * 3）、使用sql的唯一标志来告诉MyBatis执行哪个sql。sql都是保存在sql映射文件中的。
     *
     * /**
     * 	 * 1、获取sqlSessionFactory对象:
     * 	 * 		解析文件的每一个信息保存在Configuration中，返回包含Configuration的DefaultSqlSession；
     * 	 * 		注意：【MappedStatement】：代表一个增删改查的详细信息
     * 	 *
     * 	 * 2、获取sqlSession对象
     * 	 * 		返回一个DefaultSQlSession对象，包含Executor和Configuration;
     * 	 * 		这一步会创建Executor对象；
     * 	 *
     * 	 * 3、获取接口的代理对象（MapperProxy）
     * 	 * 		getMapper，使用MapperProxyFactory创建一个MapperProxy的代理对象
     * 	 * 		代理对象里面包含了，DefaultSqlSession（Executor）
     * 	 * 4、执行增删改查方法
     * 	 *
     * 	 * 总结：
     * 	 * 	1、根据配置文件（全局，sql映射）初始化出Configuration对象
     * 	 * 	2、创建一个DefaultSqlSession对象，
     * 	 * 		他里面包含Configuration以及
     * 	 * 		Executor（根据全局配置文件中的defaultExecutorType创建出对应的Executor）
     * 	 *  3、DefaultSqlSession.getMapper（）：拿到Mapper接口对应的MapperProxy；
     * 	 *  4、MapperProxy里面有（DefaultSqlSession）；
     * 	 *  5、执行增删改查方法：
     * 	 *  		1）、调用DefaultSqlSession的增删改查（Executor）；
     * 	 *  		2）、会创建一个StatementHandler对象。
     * 	 *  			（同时也会创建出ParameterHandler和ResultSetHandler）
     * 	 *  		3）、调用StatementHandler预编译参数以及设置参数值;
     * 	 *  			使用ParameterHandler来给sql设置参数
     * 	 *  		4）、调用StatementHandler的增删改查方法；
     * 	 *  		5）、ResultSetHandler封装结果
     * 	 *  注意：
     * 	 *  	四大对象每个创建的时候都有一个interceptorChain.pluginAll(parameterHandler);
     * 	 *
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        // 获取sqlSession实例，能直接执行已经映射的sql语句
        // sql的唯一标识：statement Unique identifier matching the statement to use.
        // 执行sql要用的参数：parameter A parameter object to pass to the statement.
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            Order order = openSession.selectOne("com.hong.mybatis.dao.OrderMapper.getById", 2L);
            System.out.println(order);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void test01() throws IOException {
        // 1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 2、获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            // 3、获取接口的实现类对象
            //会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            OrderMapper mapper = openSession.getMapper(OrderMapper.class);
            Order order = mapper.getById(2L);
            System.out.println(mapper.getClass());
            System.out.println(order);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void test02() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            OrderMapperAnnotation mapper = openSession.getMapper(OrderMapperAnnotation.class);
            Order order = mapper.getById(2);
            System.out.println(order);
        } finally {
            openSession.close();
        }
    }

    /**
     * 测试增删改
     * 1、mybatis允许增删改直接定义以下类型返回值
     * Integer、Long、Boolean、void
     * 2、我们需要手动提交数据
     * sqlSessionFactory.openSession();===》手动提交
     * sqlSessionFactory.openSession(true);===》自动提交
     *
     * @throws IOException
     */
    @Test
    public void test03() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //1、获取到的SqlSession不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            OrderMapper orderMapper = openSession.getMapper(OrderMapper.class);
            Order order = new Order("123", 1L, "1", "手机");
            int insert = orderMapper.addOne(order);
            System.out.println(insert);
            System.out.println(order);// 会把id设置到bean中

            Order order2 = new Order(null, null, "0", "电脑");
            order2.setId(5L);
            boolean update = orderMapper.updateOne(order2);
            System.out.println(update);

            boolean delete = orderMapper.deleteById(6L);
            System.out.println(delete);

            //2、手动提交数据
            openSession.commit();
        } finally {
            openSession.close();
        }
    }

    @Test
    public void test04() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            OrderMapper orderMapper = openSession.getMapper(OrderMapper.class);
            Map<String, Order> map = orderMapper.getListByReturnMap("1");
            map.forEach((k, v) -> System.out.println(k + "->" + v));

            List<Order> data = orderMapper.getListByUserId("1");
            System.out.println(data);

            Map<String, Object> orderMap = orderMapper.getOneByReturnMap(5L);
            orderMap.forEach((k, v) -> System.out.print(k + "->" + v + ","));
            System.out.println();

            Order order = orderMapper.getOne(5L);
            System.out.println(order);

            System.out.println("=====================");
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", 5L);
            paramMap.put("tableName", "t_order");
            Order order2 = orderMapper.getOneByMap(paramMap);
            System.out.println(order2);

            Order order3 = orderMapper.getOneByCondition("0", "789");
            System.out.println(order3);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void test05() throws Exception {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            OrderMapperPlus orderMapperPlus = openSession.getMapper(OrderMapperPlus.class);
            Order order = orderMapperPlus.getOne(5L);
            System.out.println(order);

            Order order1 = orderMapperPlus.getOneWithUserName(4L);
            System.out.println(order1);

            Order order2 = orderMapperPlus.getOneWithUserName2(3L);
            System.out.println(order2);

            Order order3 = orderMapperPlus.getOneByStep(4L);
            System.out.println(order3);

            List<Order> list = orderMapperPlus.getListByDiscriminator();
            System.out.println(list);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void test06() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = openSession.getMapper(UserMapper.class);
            User user = userMapper.getOneByIdPlus(1L);
            System.out.println(user);

            User user1 = userMapper.getOneByIdStep(1L);
            System.out.println(user1);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void testInnerParam() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            OrderMapperDynamicSql mapper = openSession.getMapper(OrderMapperDynamicSql.class);
            Order order = new Order();
            order.setOrderContent("%i%");
            List<Order> list = mapper.listByInnerParameter(order);
            list.forEach(o -> System.out.println(o));
        }finally{
            openSession.close();
        }
    }

    @Test
    public void testBatchSave() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            OrderMapperDynamicSql mapper = openSession.getMapper(OrderMapperDynamicSql.class);
            List<Order> orderList = new ArrayList<>();
            orderList.add(new Order("110",2L,"1","iPad"));
            orderList.add(new Order("119",2L,"1","Mac"));
            //int save = mapper.batchAdd(orderList);
            int save = mapper.batchAdd2(orderList);
            System.out.println(save);
            openSession.commit();
        }finally {
            openSession.close();
        }
    }

    @Test
    public void testDynamicSql() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            OrderMapperDynamicSql mapper = openSession.getMapper(OrderMapperDynamicSql.class);
            Order order = new Order();
            order.setOrderStatus("1");
            List<Order> list = mapper.getOrdersByConditionIf(order);
            list.forEach(o -> System.out.println(o));

            list = mapper.getOrdersByConditionTrim(order);
            list.forEach(o -> System.out.println(o));

            list = mapper.getOrdersByConditionChoose(order);
            list.forEach(o -> System.out.println(o));

            Order order1 = new Order();
            order1.setId(3L);
            order1.setOrderContent("iPhone");
            int update = mapper.updateSelective(order1);
            System.out.println(update);
            openSession.commit();

            list = mapper.getOrdersByConditionForeach(Arrays.asList(3L,4L,5L));
            list.forEach(o -> System.out.println(o));
        }finally{
            openSession.close();
        }
    }

}
