package restassured.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Customers {
    private String customerBaseUrl = "http://localhost:8080/customers";

    public Response createCustomer(Map<String, Object> fields){
        RestAssured.baseURI = customerBaseUrl;

        JSONObject jsonObject = new JSONObject();
        fields.keySet().forEach(field -> {
            jsonObject.put(field, fields.get(field));
        });
        return given()
                .header("Content-Type","application/json")
                .when().log().all()
                .body(jsonObject.toString())
                .post()
                .then().log().all()
                .extract().response();
    }

    public Response deleteCustomer(String firstName){
        RestAssured.baseURI = customerBaseUrl;
        return given()
                .header("Content-Type","application/json")
                .when().log().all()
                .delete("/"+firstName)
                .then().log().all()
                .extract().response();
    }

    public Response getCustomer(String firstName){
        RestAssured.baseURI = customerBaseUrl;

        return given()
                .header("Content-Type","application/json")
                .when().log().all()
                .get("/"+firstName)
                .then().log().all()
                .extract().response();
    }

    public Response updateCustomer(Map<String, Object> fields){
        RestAssured.baseURI = customerBaseUrl;

        JSONObject jsonObject = new JSONObject();
        fields.keySet().forEach(field -> {
            jsonObject.put(field, fields.get(field));
        });

        return given()
                .header("Content-Type","application/json")
                .when().log().all()
                .body(jsonObject.toString())
                .patch()
                .then().log().all()
                .extract().response();
    }
}
