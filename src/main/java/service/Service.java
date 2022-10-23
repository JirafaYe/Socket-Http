package service;

import config.UrlConfig;
import request.Request;
import response.Response;
import utils.HttpStatusCode;

public class Service {


    public Service() {
    }

    public void service(UrlConfig urlConfig,Request request,Response response){
        if(!urlConfig.getUrl().equals(request.getUrl())){
            response.setHttpStatusCode(HttpStatusCode.BAD_GATEWAY);
            return;
        }
        if(!urlConfig.getMethod().equals(request.getMethod())){
            response.setHttpStatusCode(HttpStatusCode.BAD_REQUEST);
            throw new RuntimeException("Error Method");
        }
    }
}
