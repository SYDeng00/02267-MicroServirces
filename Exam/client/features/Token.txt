#Author: Siyuan Deng

Feature: Token test

  Scenario: Get tokens as a customer
    Given the customer "First_C0000" "Last_C0000" with CPR "1230000" with balance 1000
    When the customer has registered with DTUPay
    When the customer asks for tokens
    Then the customer receives tokens