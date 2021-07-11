package testcore.testcontext;

import io.restassured.response.Response;


import java.util.HashMap;

public class ValueRepo {

    private Response restAssuredResponse;


    public HashMap<String,String> valueHashMap = new HashMap<>();

    public Response getRestAssuredResponse() {
        return restAssuredResponse;
    }

    public void setRestAssuredResponse(Response restAssuredResponse) {
        this.restAssuredResponse = restAssuredResponse;
    }
}
