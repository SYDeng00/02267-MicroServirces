#Author: Siyuan Deng

Feature: Payment

  Scenario: Payment from customer to merchant
    Given the customer "First_C8" "Last_C8" with CPR "188" with balance 1000
    And the customer has registered with DTUPay
    And the merchant "First_M8" "Lasst_M8" with CPR "454ddt" with balance 1000
    And the merchant has registered with DTUPay
    And the customer asks for 1 tokens
    And the customer receives tokens
    And the merchant initiates a payment for 50 kr by the customer
    When the merchant has received a token from the customer
    Then the payment is successful

    Scenario: Customer do not have enough money to pay
    Given the customer "First_C5360" "Last_C03360" with CPR "1236531" with balance 1000
    And the customer has registered with DTUPay
    And the merchant "First_M030630" "Last_M03362" with CPR "4516338" with balance 1000
    And the merchant has registered with DTUPay
    And the customer has registered with DTUPay
    And the customer asks for 1 tokens
    And the customer receives tokens

    And the merchant initiates a payment for 10000 kr by the customer
    When the merchant has received a token from the customer
    Then the payment is Failed


#    Scenario: Payment with invalid token
#    Given the customer "First_C0150010" "Last_C0100150" with CPR "1230500110" with balance 1000
#    And the customer has registered with DTUPay
#    And the merchant "First_M000510" "Last_M050010" with CPR "4546050010" with balance 1000
#    And the merchant has registered with DTUPay
#    And the customer has registered with DTUPay
#    And the customer asks for 1 tokens
#    And the customer receives tokens
#
#    And the merchant initiates a payment for 100 kr by the customer
#    When the merchant has received a invalid from the customer
#    Then the payment is Failed