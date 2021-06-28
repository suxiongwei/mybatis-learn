package com.sxw;

import com.sxw.entity.TUser;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.junit.Test;

import org.apache.ibatis.reflection.factory.ObjectFactory;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 苏雄伟 [suxiongwei@kaoshixing.com]
 * @description
 * @date 2020/3/27 4:07 下午
 */
public class ReflectorTest {

    @Test
    public void test(){
        // 反射工具初始化
        ObjectFactory objectFactory =  new DefaultObjectFactory();
        TUser user = objectFactory.create(TUser.class);
        ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();
        ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
        MetaObject metaObject = MetaObject.forObject(user, objectFactory, objectWrapperFactory, reflectorFactory);

        // 使用Reflector读取类元信息
        Reflector findForClass = reflectorFactory.findForClass(TUser.class);
        Constructor<?> defaultConstructor = findForClass.getDefaultConstructor();
        String[] getablePropertyNames = findForClass.getGetablePropertyNames();
        String[] setablePropertyNames = findForClass.getSetablePropertyNames();
        System.out.println(defaultConstructor.getName());
        System.out.println(Arrays.toString(getablePropertyNames));
        System.out.println(Arrays.toString(setablePropertyNames));

        // 使用ObjectWrapper读取对象信息，并赋值
        TUser userTmp = new TUser();
        ObjectWrapper wrapperForUser = new BeanWrapper(metaObject, userTmp);
        String[] getterNames = wrapperForUser.getGetterNames();
        String[] setterNames = wrapperForUser.getSetterNames();
        System.out.println(Arrays.toString(getterNames));
        System.out.println(Arrays.toString(setterNames));

        PropertyTokenizer prop = new PropertyTokenizer("userName");
        wrapperForUser.set(prop, "su");
        System.out.println(userTmp);



    }

    @Test
    public void testReflector(){
        // 反射工具初始化
        ObjectFactory objectFactory =  new DefaultObjectFactory();
        TUser user = objectFactory.create(TUser.class);
        ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();
        ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
        MetaObject metaObject = MetaObject.forObject(user, objectFactory, objectWrapperFactory, reflectorFactory);

        // 模拟数据库行数据转换为对象
        // 1.模拟从数据库读取数据
        Map<String, Object> dbResult = new HashMap<String, Object>();
        dbResult.put("id", 1);
        dbResult.put("user_name", "sungou");
        dbResult.put("real_name", "孙笑川");

        // 2.模拟映射关系
        Map<String, String> mapper = new HashMap<String, String>();
        mapper.put("id", "id");
        mapper.put("userName", "user_name");
        mapper.put("realName", "real_name");

        // 3.使用反射工具类将行数据转换为POJO
        BeanWrapper beanWrapper = (BeanWrapper) metaObject.getObjectWrapper();
        for (Map.Entry<String, String> colInfo : mapper.entrySet()) {
            String propName = colInfo.getKey();
            Object propValue = dbResult.get(colInfo.getValue());
            PropertyTokenizer propertyTokenizer = new PropertyTokenizer(propName);
            beanWrapper.set(propertyTokenizer, propValue);
        }
        System.out.println(metaObject.getOriginalObject());
    }
}
