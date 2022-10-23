package request;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpParser;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    static Pattern pattern=Pattern.compile("(GET|PUT|POST|DELETE|OPTIONS|TRACE|HEAD)");
    private String body;
    private final String method;
    private String url;
    private HashMap<String,String> headers;
    private HashMap<String, Object> params;

    public String getBody() {
        return body;
    }

    public String getMethod() {
        return method;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public String getUrl() {
        return url;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    public void setParams(String url) {
        params = new HashMap<>();
        String[] split = url.split("\\?");
        if(split.length<=1)
            return;
        this.url=split[0];
        //根据&分割参数
        String[] kv = split[1].split("[&]");
        for (String s : kv) {
            //解析出键值对
            String[] arrSplit = s.split("[=]");
            if(arrSplit.length>1)
                params.put(arrSplit[0],arrSplit[1]);
            else{
                //如果参数值为空则只加入参数名
                if(!arrSplit[0].isEmpty())
                    params.put(arrSplit[0],"");
            }
        }

    }


    public Request(Socket socket) throws IOException {
        headers = new HashMap<>();
        params = new HashMap<>();
        InputStream inputStream = socket.getInputStream();
        String methodline = HttpParser.readLine(inputStream, "utf-8");
        System.out.println("methodline-->"+methodline);


        Matcher matcher = pattern.matcher(methodline);
        matcher.find();
        //获取方法
        this.method = matcher.group();

        String[] split = methodline.split(" ");
        this.url=split[1];

        //获取请求头
        for (Header header : HttpParser.parseHeaders(inputStream, "utf-8")) {
            this.headers.put(header.getName(), header.getValue());
        }

        //获取请求体
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        char[] buffer;
        if(headers.containsKey("Content-Length"))
            buffer=new char[Integer.parseInt(headers.get("Content-Length"))];
        else
            buffer=new char[1024];
        StringBuilder stringBuilder = new StringBuilder();
        while (inputStream.available() > 0){
            bufferedReader.read(buffer);
            stringBuilder.append(buffer);
        }
        this.body=stringBuilder.toString();

        setParams(url);
    }

    @Override
    public String toString() {
        return "Request{" +
                "body='" + body + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", headers=" + headers +
                ", params=" + params +
                '}';
    }
}
