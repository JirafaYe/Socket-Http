package config;

import java.util.List;

public class UrlConfig {
    private String url;
    private String method;
    private String className;
    private String methodName;

    public UrlConfig() {
    }

    public UrlConfig(String url, String method, String className, String methodName) {
        this.url = url;
        this.method = method;
        this.className = className;
        this.methodName = methodName;
    }

    public UrlConfig(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "UrlConfig{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                '}';
    }
}
