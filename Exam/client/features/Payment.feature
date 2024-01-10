#Author: Siyuan Deng

Feature: Payment

  Scenario: Payment from customer to merchant
    Given the customer "First_C00010" "Last_C00010" with CPR "12300010" with balance 1000
    And the customer has registered with DTUPay
    And the merchant "First_M00010" "Last_M00010" with CPR "454600010" with balance 1000
    And the merchant has registered with DTUPay
    And the customer has registered with DTUPay
    And the customer asks for tokens
    And the customer receives tokens

    And the merchant initiates a payment for 50 kr by the customer
    When the merchant has received a token from the customer
    Then the payment is successful