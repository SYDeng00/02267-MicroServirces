#Author: Siyuan Deng

Feature: Token test

  Scenario: Get tokens as a customer
    Given the customer "Token11" "Token21" with CPR "Token31" with balance 1000
    When the customer has registered with DTUPay
    When the customer asks for 1 tokens
    Then the customer receives tokens