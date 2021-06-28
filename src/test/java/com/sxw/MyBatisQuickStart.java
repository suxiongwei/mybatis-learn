package com.sxw;

import com.sxw.entity.TUser;
import com.sxw.mapper.TUserMapper;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

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
        // 再查一遍 测试一级缓存
        TUser user1 = userMapper.selectByPrimaryKey(1);
        System.out.println(user1.toString());
    }

    @Test
    public void quickStart1(){
        // 2.获取sqlSession 这个SqlSession在请求完之后就关闭了
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 3.执行查询语句并返回结果
        TUser user = sqlSession.selectOne("com.sxw.mapper.TUserMapper.selectByPrimaryKey", 1);
        System.out.println(user.toString());
        // 再查一遍 测试一级缓存
        TUser user1 = sqlSession.selectOne("com.sxw.mapper.TUserMapper.selectByPrimaryKey", 1);
        System.out.println(user1.toString());
    }

    /**
     *
     * @throws IOException
     */
    @Test
    public void testConfiguration() throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        // 创建XMLConfigBuilder
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader);
        // 调用parse方法 解析xml创建configuration对象
        Configuration configuration = xmlConfigBuilder.parse();
    }

    @Test
    public void testInsertBatch(){
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        TUserMapper userMapper = sqlSession.getMapper(TUserMapper.class);
        for (int i = 0; i < 5; i++) {
            TUser user = new TUser();
            user.setSex(1);
            user.setUserName("孙笑川-" + i);
            user.setRealName("孙狗-" + i);
            user.setMobile("17600001111");
            user.setEmail("sungou@gou.com");
            user.setNoteDetail("日本天皇");

            userMapper.insert(user);
        }
        sqlSession.commit();
        sqlSession.clearCache();
        sqlSession.close();
    }
}
