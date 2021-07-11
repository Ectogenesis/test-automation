Feature: Test of customer REST services

  @delete-customer
  Scenario:Create a customer successfully
    Given Create a customer with given fields
      | first_name | last_name |
      | eren       | aksu      |
    Then Status code must be "201"

  @delete-customer
  Scenario: Check if same customer can be created twice
    Given Create a customer with given fields
      | first_name | last_name |
      | eren       | aksu      |
    And Status code must be "201"
    And Create a customer with given fields
      | first_name | last_name |
      | eren       | aksu      |
    And Status code must be "409"
    And "code" must be "Customer_already_exists"

  @delete-customer #needs to be discussed if customer should be created without lastName or not
  Scenario: Try to create customer without last name
    Given Create a customer with given fields
      | first_name |
      | eren       |
    And Status code must be "201"
    And "first_name" must be "eren"
    And "last_name" must be "NULL"

  @delete-customer
  Scenario: GET created customer
    Given Create a customer with given fields
      | first_name | last_name |
      | testAdmin  | TEST      |
    And Status code must be "201"
    And Get created user
    And Status code must be "200"
    And "first_name" must be "testAdmin"
    And "last_name" must be "TEST"


    #This test case should be discussed with dev's or business analyst to decide if status code must be 200 or 404
  Scenario: Try to GET non-existing customer
    Given Get customer with "TEST123TEST" first name
    And Response must be null
    And Status code must be "404"

    #This test case should be discussed with dev's or business analyst to decide if status code must be 200 or 404
  Scenario: Try to GET Deleted customer
    Given Create a customer with given fields
      | first_name | last_name |
      | testAdmin  | TEST      |
    And Status code must be "201"
    And Delete customer thats firstName is "testAdmin"
    And Status code must be "204"
    And Get customer with "testAdmin" first name
    And Status code must be "404"

  @delete-customer
  Scenario: Try to GET updated customer
    Given Create a customer with given fields
      | first_name | last_name |
      | Eren       | Aksu      |
    And Status code must be "201"
    And Update customer with given fields by using PATCH method
      | first_name | last_name    |
      | Eren       | testtesttest |
    And Status code must be "204"
    And Get customer with "Eren" first name
    And "last_name" must be "testtesttest"


  Scenario: Delete existing customer
    Given Create a customer with given fields
      | first_name | last_name |
      | Eren       | Aksu      |
    And Status code must be "201"
    And Delete customer thats firstName is "Eren"
    And Status code must be "204"
    And Get customer with "Eren" first name
    And Response must be null

    #Clarify with the team that if status code should be 404 or 204
  Scenario: Delete non-existing customer
    And Delete customer thats firstName is "TEST6789TEST"
    And Status code must be "204"


  Scenario: Delete updated customer
    Given Create a customer with given fields
      | first_name   | last_name           |
      | TestCustomer | TestCustomerSurname |
    And Status code must be "201"
    And Update customer with given fields by using PATCH method
      | first_name   | last_name               |
      | TestCustomer | TestCustomerSurname1234 |
    And Delete customer thats firstName is "TestCustomer"
    And Status code must be "204"
    And Get customer with "TestCustomer" first name
    And Response must be null

  Scenario: Try to update non-existing customer
    Given Update customer with given fields by using PATCH method
      | first_name | last_name |
      | TEST1234   | 4321TEST  |
    And Status code must be "404"
    And "code" must be "Customer_not_found_with_given_first_name"

    #this case must be clarified if response code must be 400 or 204 once first name is not sent in payload
    Scenario: Try to update without first name
      Given Update customer with given fields by using PATCH method
        | last_name |
        | 4321TEST  |
      And Status code must be "404"
      And "code" must be "Customer_not_found_with_given_first_name"