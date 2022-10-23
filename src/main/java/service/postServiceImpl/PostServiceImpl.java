package service.postServiceImpl;

import com.google.gson.Gson;
import entity.TestPostEntity;
import request.Request;
import response.Response;
import service.Service;
import utils.HttpStatusCode;

public class PostServiceImpl extends Service{
    public void testPost(Request request, Response resp){
        String body = request.getBody();
        TestPostEntity testPostEntity = new Gson().fromJson(body, TestPostEntity.class);
        resp.setBody(testPostEntity);
        resp.setHttpStatusCode(HttpStatusCode.OK);
    }
}
