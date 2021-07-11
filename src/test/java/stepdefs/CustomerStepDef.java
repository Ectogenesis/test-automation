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
        Assert.assertEquals(valueRepo.getRestAssuredResponse().getBody().path(key),value);
    }
}

