#Author: Siyuan Deng

Feature: Payment

  Scenario: Payment from customer to merchant
    Given the customer "First_C01ssds0ddd010" "Last_C00d1s1sw0dd1s0" with CPR "1d21dsddsdd0fs00s10" with balance 1000
    And the customer has registered with DTUPay
    And the merchant "First_M0ssdsddd10010" "Lasst_M1sd0d0ds010" with CPR "454dd6ssddfdds1ffs0010" with balance 1000
    And the merchant has registered with DTUPay
    And the customer asks for 1 tokens
    And the customer receives tokens
    And the merchant initiates a payment for 50 kr by the customer
    When the merchant has received a token from the customer
    Then the payment is successful
