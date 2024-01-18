#Author: Siyuan Deng

Feature: Payment

 

    Scenario: Customer do not have enough money to pay
    Given the customer "First_C50" "Last_C030" with CPR "1d23e51" with balance 1000
    And the customer has registered with DTUPay
    And the merchant "First_M0300" "Last_M032" with CPR "451de38" with balance 1000
    And the merchant has registered with DTUPay
    And the customer has registered with DTUPay
    And the customer asks for 1 tokens
    And the customer receives tokens
    And the merchant initiates a payment for 10000 kr by the customer
    When the merchant has received a token from the customer
    Then the payment is Failed
