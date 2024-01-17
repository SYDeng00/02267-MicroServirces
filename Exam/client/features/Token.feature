#Author: Siyuan Deng

Feature: Token test

  Scenario: Get tokens as a customer
    Given the customer "First_C0101001" "Last_C0110001" with CPR "1230101001" with balance 1000
    When the customer has registered with DTUPay
    When the customer asks for 1 tokens
    Then the customer receives tokens