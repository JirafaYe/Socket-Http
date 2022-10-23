package com.jirafa.test;

import config.UrlConfig;
import org.apache.commons.httpclient.util.DateUtil;
import org.junit.Test;
import request.Request;
import response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;

public class TestDemo {
    @Test
    public void testGetPropertise() throws IOException {
        InputStream inputStream = TestDemo.class.getClassLoader().getResourceAsStream("server.porperties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String getPath="server./test";
        UrlConfig urlConfig = new UrlConfig();
        urlConfig.setMethod(properties.getProperty(getPath+".method"));
        urlConfig.setUrl("/test");
        urlConfig.setClassName(properties.getProperty(getPath+".className"));
        urlConfig.setMethodName(properties.getProperty(getPath+".methodName"));
//        List<String> params = new LinkedList<>(Arrays.asList(properties.getProperty(getPath + ".params").split(",")));
        System.out.println(urlConfig);
    }

    @Test
    public void testReflect() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<?> serviceClass = Class.forName("service.getServiceImpl.GetServiceImpl");
        System.out.println(serviceClass.getName());
//        GetServiceImpl o = (GetServiceImpl) serviceClass.newInstance();
        Method method = serviceClass.getDeclaredMethod("testService", Request.class, Response.class);
        System.out.println(method);
//        Object o = serviceClass.getConstructor(UrlConfig.class, Request.class, Response.class).newInstance(urlConfig,request,response);
//        method.invoke(o,request, response);

    }

    @Test
    public void testDate(){
        String s = DateUtil.formatDate(new Date());
        System.out.println(s);
    }


}
