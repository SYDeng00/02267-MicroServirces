Feature: DTU Pay User Registration

  @register
  Scenario: Customer registration to DTU Pay
    Given has a bank account with a valid bank account id
    And provides their details to DTUPay starting with first name, last name, CPR & Bank account id
    Then is registering with DTUPay as a customer

  @register
  Scenario: Nonexistent bank id
    Given user gives non existing bank account id
    Then is given an error message

#  @register
#  Scenario: Wrong registration input
#    Given user gives empty input
#    Then is given an error message