package stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.junit.Assert;
import restassured.tests.Customers;
import testcore.testcontext.ValueRepo;

import java.util.List;
import java.util.Map;

public class CustomerStepDef {
    Customers customers;
    ValueRepo valueRepo;
    public CustomerStepDef(){
        customers = new Customers();
        valueRepo = new ValueRepo();
    }

    @Given("Create a customer with given fields")
    public void createACustomerWithGivenFields(DataTable dataTable) {
        List<Map<String, Object>> tableRows = dataTable.asMaps(String.class, String.class);
        Map<String,Object> columns = tableRows.get(0);
        Response response = customers.createCustomer(columns);
        if(response.getBody().path("first_name") != null)
            valueRepo.valueHashMap.put("createdCustomer",response.getBody().path("first_name"));
        valueRepo.valueHashMap.put("responseStatusCode",String.valueOf(response.statusCode()));
        valueRepo.setRestAssuredResponse(response);
    }

    @Then("Status code must be {string}")
    public void statusCodeMustBe(String statusCode) {
        Assert.assertEquals(statusCode, valueRepo.valueHashMap.get("responseStatusCode"));
    }
    @After(value = "@delete-customer")
    public void deleteCustomerAnnotation(){
        customers.deleteCustomer(valueRepo.valueHashMap.get("createdCustomer"));
    }

    @And("{string} must be {string}")
    public void mustBe(String key, String value) {
        if(value.equals("NULL"))
            Assert.assertNull(valueRepo.getRestAssuredResponse().getBody().path(key));
        else
            Assert.assertEquals(valueRepo.getRestAssuredResponse().getBody().path(key),value);
    }

    @And("Get created user")
    public void getCreatedUser() {
        Response response = customers.getCustomer(valueRepo.valueHashMap.get("createdCustomer"));
        valueRepo.valueHashMap.put("responseStatusCode",String.valueOf(response.statusCode()));
        valueRepo.setRestAssuredResponse(response);
    }

    @Given("Get customer with {string} first name")
    public void getCustomerWithFirstName(String firstName) {
        Response response = customers.getCustomer(firstName);
        valueRepo.valueHashMap.put("responseStatusCode",String.valueOf(response.statusCode()));
        valueRepo.setRestAssuredResponse(response);
    }

    @And("Response must be null")
    public void responseMustBeNull() {
        Assert.assertEquals("",valueRepo.getRestAssuredResponse().asPrettyString());
    }

    @And("Update customer with given fields by using PATCH method")
    public void updateCustomerWithGivenFieldsByUsingPATCHMethod(DataTable dataTable) {
        List<Map<String, Object>> tableRows = dataTable.asMaps(String.class, String.class);
        Map<String,Object> columns = tableRows.get(0);
        Response response = customers.updateCustomer(columns);
        valueRepo.valueHashMap.put("responseStatusCode",String.valueOf(response.statusCode()));
        valueRepo.setRestAssuredResponse(response);
    }

    @And("Delete customer thats firstName is {string}")
    public void deleteCustomerThatSFirstNameIs(String firstName) {
        Response response = customers.deleteCustomer(firstName);
        valueRepo.valueHashMap.put("responseStatusCode",String.valueOf(response.statusCode()));
    }
}

