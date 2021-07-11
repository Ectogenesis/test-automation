Feature: Test of customer REST servics

  @delete-customer
  Scenario:Create a customer successfully
    Given Create a customer with given fields
      | first_name | last_name |
      | eren       | aksu      |
    Then Status code must be "201"

  @delete-customer
  Scenario: Check if same user can be created twice
    Given Create a customer with given fields
      | first_name | last_name |
      | eren       | aksu      |
    And Status code must be "201"
    And Create a customer with given fields
      | first_name | last_name |
      | eren       | aksu      |
    And Status code must be "409"
    And "code" must be "Customer_already_exists"