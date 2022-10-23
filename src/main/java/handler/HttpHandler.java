package handler;

import com.google.gson.Gson;
import config.UrlConfig;
import request.Request;
import response.Response;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class HttpHandler  {

    public HttpHandler() {
    }

    public void doHandle(Request req, Response resp, UrlConfig urlConfig) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        if(urlConfig.getClassName()!=null)
        {
            System.out.println("开始调用");
            System.out.println(urlConfig);
            Class<?> serviceClass = Class.forName(urlConfig.getClassName());
            Method method = serviceClass.getDeclaredMethod(urlConfig.getMethodName(), Request.class, Response.class);
            Method service = serviceClass.getMethod("service", UrlConfig.class, Request.class, Response.class);
            Object o = serviceClass.newInstance();
            service.invoke(o,urlConfig,req,resp);
            method.invoke(o, req, resp);
        }else {
            throw new RuntimeException("url Error");
        }
    }

    public void sendResponse(Response resp, BufferedWriter os) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        resp.setContentLength(new Gson().toJson(resp.getBody()).length());
        resp.setHeaders();
        os.write(resp.getHeaders());
        os.write(new Gson().toJson(resp.getBody()));
        os.flush();
    }

    public UrlConfig getUrlConfig(String url) throws IOException {
        String path="server."+url;
        InputStream inputStream = HttpHandler.class.getClassLoader().getResourceAsStream("server.porperties");
        Properties properties = new Properties();
        properties.load(inputStream);
        UrlConfig urlConfig = new UrlConfig(url);
        urlConfig.setMethod(properties.getProperty(path+".method"));
        urlConfig.setClassName(properties.getProperty(path+".className"));
        urlConfig.setMethodName(properties.getProperty(path+".methodName"));
        return urlConfig;
    }
}
