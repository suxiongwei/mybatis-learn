package com.sxw;

import com.sxw.entity.TUser;
import com.sxw.mapper.TUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 苏雄伟 [suxiongwei@kaoshixing.com]
 * @description
 * @date 2020/3/25 2:05 下午
 */
public class MyBatisQuickStart {
    private SqlSessionFactory sqlSessionFactory;

    // 源码分析的入口
    @Before
    public void init() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 1.读取mybatis配置文件创建 sqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void quickStart(){
        // 2.获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        // 3.获取对应mapper
        TUserMapper userMapper = sqlSession.getMapper(TUserMapper.class);
        // 4.执行查询语句并返回结果
        TUser user = userMapper.selectByPrimaryKey(1);
        System.out.println(user.toString());

    }
}
