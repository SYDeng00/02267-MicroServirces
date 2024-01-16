#Author: Siyuan Deng

Feature: Token test

  Scenario: Get tokens as a customer
    Given the customer "First_C010001" "Last_C010001" with CPR "123010001" with balance 1000
    When the customer has registered with DTUPay
    When the customer asks for 3 tokens
    Then the customer receives tokens