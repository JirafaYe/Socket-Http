package service.getServiceImpl;

import request.Request;
import response.Response;
import service.Service;
import utils.HttpStatusCode;

import java.util.HashMap;

public class GetServiceImpl extends Service{

    public void testService(Request req, Response resp){
        HashMap<String, Object> params = req.getParams();
        int a;
        String b;
        if(params.containsKey("a")&& params.containsKey("b")) {
            a = Integer.parseInt((String) params.get("a"));
            b = (String) params.get("b");
        }else {
            resp.setHttpStatusCode(HttpStatusCode.BAD_REQUEST);
            return;
        }
        if(Integer.parseInt(b)==a){
            resp.setHttpStatusCode(HttpStatusCode.OK);
            resp.setBody("server on");
            System.out.println("res: "+resp);
            return;
        }
        resp.setHttpStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
}
