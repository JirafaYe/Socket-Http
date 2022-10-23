package response;

import com.google.gson.Gson;
import org.apache.commons.httpclient.util.DateUtil;
import utils.HttpStatusCode;


import java.util.Date;

public class Response {

    private HttpStatusCode httpStatusCode;
    private String headers;
    private String msg;
    private String contentType;
    private Integer contentLength;
    private String  body;
    public Response(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        this.msg = null;
        this.body =null;
    }

    public Response() {
    }

    public Response(HttpStatusCode httpStatusCode, String msg, String contentType, Integer contentLength, String  object,String headers) {
        this.httpStatusCode = httpStatusCode;
        this.msg = msg;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.body = object;
        this.headers = headers;
    }

    public String getHeaders() {
        return headers;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getContentLength() {
        return contentLength;
    }

    public void setContentLength(Integer contentLength) {
        this.contentLength = contentLength;
    }

    public void setHeaders(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP/1.1 ").append(this.httpStatusCode).append("\n");
        stringBuilder.append("Date: ").append(DateUtil.formatDate(new Date())).append("\n");
        if(contentType!=null&&contentLength!=null) {
            stringBuilder.append("Content-Type: ").append(getContentType()).append("\n");
            stringBuilder.append("Content-Length: ").append(getContentLength()).append("\n");
        }
        stringBuilder.append("\n");
        this.headers = stringBuilder.toString();
    }

    public void setBody(Object object){
        this.body =new Gson().toJson(object);
        this.contentLength=body.length();
        System.out.println("body-->"+body);
    }



    public String getMsg() {
        return msg;
    }

    public String  getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Response{" +
                "httpStatusCode=" + httpStatusCode +
                ", msg='" + msg + '\'' +
                ", contentType='" + contentType + '\'' +
                ", contentLength=" + contentLength +
                ", object=" + body +
                '}';
    }
}
